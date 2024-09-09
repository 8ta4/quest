(ns background
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/browser.runtime.onMessage.addListener (fn [message _ send-response]
                                              (js/console.log "Received message")
                                              (js/console.log message)
                                              (-> message
                                                  http/get
                                                  <!
                                                  :body
                                                  send-response
                                                  go)
                                              true)))
