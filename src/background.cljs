(ns background
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/browser.runtime.onMessage.addListener (fn [message]
                                              (js/console.log "Received message")
                                              (js/console.log message)
                                              (go (:body (<! (http/get message)))))))
