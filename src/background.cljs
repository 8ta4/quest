(ns background
  (:require [clojure.set :refer [map-invert]]
            [com.rpl.specter :refer [ATOM multi-path NONE setval]]
            [core]
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
  (js/chrome.runtime.onConnect.addListener (fn [port]
                                             (when-let [quest ((:answer-quest @state) port.sender.tab.id)]
                                               (js/console.log "Answer tab connected")
                                               (js-await [response (fetch/get quest)]
                                                         (.postMessage port (clj->js {:data (:body response)
                                                                                      :action core/init})))
                                               (create-question-window port))
                                             (when-let [port* ((:question-port @state) port.sender.tab.id)]
                                               (js/console.log "Question tab connected")
                                               (.addListener port*.onMessage
                                                             (fn [message]
                                                               (js/console.log "Received message from answer tab")
                                                               (.postMessage port message)))
                                               (.postMessage port* (clj->js {:action core/sync})))
                                             (.addListener port.onDisconnect
                                                           #(when-let [id ((invert-and-merge (:answer-question @state))
                                                                           port.sender.tab.id)]
                                                              (eval-path-setval [ATOM
                                                                                 (multi-path :answer-quest
                                                                                             :answer-question
                                                                                             :question-port)
                                                                                 (multi-path id
                                                                                             port.sender.tab.id)]
                                                                                NONE
                                                                                state)
                                                              (js/chrome.tabs.remove id)))))
  (js/chrome.webNavigation.onCommitted.addListener (fn [details]
                                                     (when-let [quest (:quest (query-map details.url))]
                                                       (js/console.log "URL with quest query committed")
                                                       (eval-path-setval [ATOM :answer-quest details.tabId] quest state))))
;; https://developer.chrome.com/docs/extensions/develop/concepts/service-workers/lifecycle#:~:text=Chrome%20terminates%20a,resets%20this%20timer.
;; The following line is used to periodically call an extension API to prevent the service worker
;; from being terminated due to inactivity. Chrome terminates a service worker after 30 seconds
;; of inactivity, but calling an extension API like `chrome.runtime.getPlatformInfo` resets this timer.
;; This ensures that the service worker remains active and responsive to events.
  (js/setInterval js/chrome.runtime.getPlatformInfo 10000)
  (when js/goog.DEBUG
    (js-await [windows (js/chrome.windows.getAll)]
              (->> (js->clj windows {:keywordize-keys true})
                   (map :id)
                   (run! js/chrome.windows.remove))
              (js/chrome.windows.create (clj->js {:url "http://localhost:8000?quest=http://localhost:8000/index.yaml"})))))
