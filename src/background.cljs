(ns background
  (:require [shadow.cljs.modern :refer [js-await]]
            [lambdaisland.fetch :as fetch]))

(when js/goog.DEBUG
  (defn remove-popup-windows []
    (js-await [windows (js/chrome.windows.getAll)]
              (->> (js->clj windows {:keywordize-keys true})
                   (filter (comp (partial = "popup")
                                 :type))
                   (map :id)
                   (run! js/chrome.windows.remove)))))

(defn create-question-window [id]
  (js/chrome.windows.create (clj->js {:url (str "localhost:8000/question.html?id=" id)
                                      :type "popup"})))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onMessage.addListener (fn [message sender send-response]
                                             (js/console.log "Received message")
                                             (js/console.log message)
                                             (js-await [response (fetch/get message)]
                                                       (send-response (:body response)))
                                             (when js/goog.DEBUG
                                               (remove-popup-windows))
                                             (create-question-window sender.tab.id)
                                             true)))
