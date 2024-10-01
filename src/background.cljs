(ns background
  (:require [com.rpl.specter :refer [ATOM setval]]
            [lambdaisland.fetch :as fetch]
            [lambdaisland.uri :refer [query-map]]
            [shadow.cljs.modern :refer [js-await]]))

(def state (atom {:answer {}}))

(defn eval-path-setval
  "Sets the value `aval` at the specified path `apath` within the `structure`.
   The path is dynamically evaluated, allowing for runtime determination of where
   the value should be set."
  [apath aval structure]
  (setval apath aval structure))

(when js/goog.DEBUG
  (defn remove-popup-windows []
    (js-await [windows (js/chrome.windows.getAll)]
              (->> (js->clj windows {:keywordize-keys true})
                   (filter (comp (partial = "popup")
                                 :type))
                   (map :id)
                   (run! js/chrome.windows.remove)))))

(defn create-question-window [port]
  (js/chrome.windows.create (clj->js {:url "http://localhost:8000/question.html"
                                      :type "popup"})
                            (fn [window]
                              (eval-path-setval [ATOM
                                                 :question
                                                 (-> window
                                                     (js->clj {:keywordize-keys true})
                                                     :tabs
                                                     first
                                                     :id)]
                                                port
                                                state))))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onConnect.addListener (fn [port]
                                             (when-let [quest ((:answer @state) port.sender.tab.id)]
                                               (js/console.log "Answer window connected")
                                               (js-await [response (fetch/get quest)]
                                                         (.postMessage port (:body response)))
                                               (when js/goog.DEBUG
                                                 (remove-popup-windows))
                                               (create-question-window port))
                                             (when-let [port* ((:question @state) port.sender.tab.id)]
                                               (js/console.log "Question window connected")
                                               (.addListener port*.onMessage
                                                             (fn [message]
                                                               (js/console.log "Received message from answer window")
                                                               (.postMessage port message))))))
  (js/chrome.webNavigation.onCommitted.addListener (fn [details]
                                                     (when-let [quest (:quest (query-map details.url))]
                                                       (js/console.log "URL with quest query committed")
                                                       (eval-path-setval [ATOM :answer details.tabId] quest state)))))
