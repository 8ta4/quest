(ns background
  (:require [shadow.cljs.modern :refer [js-await]]
            [lambdaisland.fetch :as fetch]))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onConnect.addListener
   (fn [port]
     (js/console.log "Connected to content script")
     (js/console.log port.name)
     (js-await [response (fetch/get port.name)]
               (.postMessage port (:body response))))))
