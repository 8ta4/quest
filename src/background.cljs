(ns background
  (:require [clojure.set :refer [map-invert]]
            [com.rpl.specter :refer [ATOM setval]]
            [lambdaisland.fetch :as fetch]
            [lambdaisland.uri :refer [query-map]]
            [shadow.cljs.modern :refer [js-await]]))

(def state (atom {:answer-quest {}
                  :question-port {}
                  :answer-question {}}))

(defn eval-path-setval
  "Sets the value `aval` at the specified path `apath` within the `structure`.
   The path is dynamically evaluated, allowing for runtime determination of where
   the value should be set."
  [apath aval structure]
  (setval apath aval structure))

(defn create-question-window [port]
;; Using the production URL during development can lead to a DOMException: "The operation is insecure."
  (js/chrome.windows.create (clj->js {:url (str (if js/goog.DEBUG
                                                  "http://localhost:8000"
                                                  "https://8ta4.github.io/quest/public")
                                                "/question.html")
                                      :type "popup"})
                            (fn [window]
                              (let [id (-> window
                                           (js->clj {:keywordize-keys true})
                                           :tabs
                                           first
                                           :id)]
                                (eval-path-setval [ATOM
                                                   :question-port
                                                   id]
                                                  port
                                                  (eval-path-setval [ATOM
                                                                     :answer-question
                                                                     port.sender.tab.id]
                                                                    id
                                                                    state))))))

(defn invert-and-merge [m]
  (merge m (map-invert m)))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.tabs.onRemoved.addListener (fn [tab-id]
                                          (when-let [tab-id* ((invert-and-merge (:answer-question @state)) tab-id)]
                                            (js/chrome.tabs.remove tab-id*))))
  (js/chrome.runtime.onConnect.addListener (fn [port]
                                             (when-let [quest ((:answer-quest @state) port.sender.tab.id)]
                                               (js/console.log "Answer tab connected")
                                               (js-await [response (fetch/get quest)]
                                                         (.postMessage port (clj->js {:data (:body response)
                                                                                      :action "init"})))
                                               (create-question-window port))
                                             (when-let [port* ((:question-port @state) port.sender.tab.id)]
                                               (js/console.log "Question tab connected")
                                               (.addListener port*.onMessage
                                                             (fn [message]
                                                               (js/console.log "Received message from answer tab")
                                                               (.postMessage port message)))
                                               (.postMessage port* (clj->js {:action "sync"})))))
  (js/chrome.webNavigation.onCommitted.addListener (fn [details]
                                                     (when-let [quest (:quest (query-map details.url))]
                                                       (js/console.log "URL with quest query committed")
                                                       (eval-path-setval [ATOM :answer-quest details.tabId] quest state))))
  (when js/goog.DEBUG
    (js-await [windows (js/chrome.windows.getAll)]
              (->> (js->clj windows {:keywordize-keys true})
                   (map :id)
                   (run! js/chrome.windows.remove))
              (js/chrome.windows.create (clj->js {:url "http://localhost:8000?quest=http://localhost:8000/index.yaml"})))))
