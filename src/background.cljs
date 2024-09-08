(ns background)

(defn init
  []
  (js/console.log "Background script initialized")
  (js/browser.runtime.onMessage.addListener (fn [message]
                                              (js/console.log "Received message")
                                              (js/console.log message))))
