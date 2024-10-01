(ns question)

(defn init
  []
  (js/console.log "Initializing the question module")
  (.onMessage.addListener (js/chrome.runtime.connect)
                          (fn [message])))