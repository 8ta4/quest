(ns content
  (:require [lambdaisland.uri :refer [query-map]]))

(def quest
  (:quest (query-map js/location.href)))