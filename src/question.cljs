(ns question
  (:require [lambdaisland.uri :refer [query-map]]))

(def id
  (:id (query-map js/location.href)))

(defn init
  []
  (js/console.log "Question script initialized"))