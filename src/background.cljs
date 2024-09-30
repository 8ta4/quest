(ns background)

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.webNavigation.onCommitted.addListener (fn [details])))
