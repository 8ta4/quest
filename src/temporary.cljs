(ns temporary)

(defn init
  []
  (js/console.log "close init")
  (js/setTimeout js/window.close))