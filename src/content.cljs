(ns content
  (:require [lambdaisland.uri :refer [query-map]]
            [yaml :refer [parse]]))

(def quest
  (:quest (query-map js/location.href)))

(defn init
  []
  (when quest
    (.then (js/browser.runtime.sendMessage quest)
           (fn [response]
             (js/console.log "Received response from background script")
             (js->clj (parse response))))))