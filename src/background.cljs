(ns background
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [shadow.cljs.modern :refer [js-await]]))

(when js/goog.DEBUG
  (defn remove-popup-windows []
    (js-await [windows (js/chrome.windows.getAll)]
              (->> (js->clj windows {:keywordize-keys true})
                   (filter (comp (partial = "popup")
                                 :type))
                   (map :id)
                   (run! js/chrome.windows.remove)))))

(defn create-question-window [id]
  (js/chrome.windows.create (clj->js {:url (str "question.html?id=" id)
                                      :type "popup"})))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onMessage.addListener (fn [message sender send-response]
                                             (js/console.log "Received message")
                                             (js/console.log message)
                                             (-> message
                                                 http/get
                                                 <!
                                                 :body
                                                 send-response
                                                 go)
                                             (when js/goog.DEBUG
                                               (remove-popup-windows))
                                             (create-question-window sender.tab.id)
                                             true)))
