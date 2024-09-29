(ns background
  (:require [shadow.cljs.modern :refer [js-await]]
            [lambdaisland.fetch :as fetch]))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onConnect.addListener
   (fn [port]
     (js/console.log "Connected to content script")
     (port.onMessage.addListener
      (fn [message]
        (js/console.log "Received message")
        (js/console.log message)
        (js-await [response (fetch/get message)]
                  (.postMessage port (:body response))))))))
