(ns content
  (:require [lambdaisland.uri :refer [query-map]]))

(def quest
  (:quest (query-map js/location.href)))

(defn init
  []
  (when quest
    (js/browser.runtime.sendMessage quest)))