(ns background
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [shadow.cljs.modern :refer [js-await]]))

(defn remove-popup-windows []
  (js-await [windows (js/chrome.windows.getAll)]
            (->> (js->clj windows {:keywordize-keys true})
                 (filter (comp (partial = "popup")
                               :type))
                 (map :id)
                 (run! js/chrome.windows.remove))))

(when js/goog.DEBUG
  (defn create-question-window []
    (js/chrome.windows.create (clj->js {:url "question.html"
                                        :type "popup"}))))

(defn init
  []
  (js/console.log "Background script initialized")
  (when js/goog.DEBUG
    (remove-popup-windows))
  (create-question-window)
  (js/chrome.runtime.onMessage.addListener (fn [message _ send-response]
                                             (js/console.log "Received message")
                                             (js/console.log message)
                                             (-> message
                                                 http/get
                                                 <!
                                                 :body
                                                 send-response
                                                 go)
                                             true)))
