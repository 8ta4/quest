(ns content
  (:require [lambdaisland.uri :refer [query-map]]))

(def quest
  (:quest (query-map js/location.href)))

(defn init
  []
  (when quest
    (.then (js/browser.runtime.sendMessage quest)
           (fn [response]
             (js/console.log "Received response from background script")))))