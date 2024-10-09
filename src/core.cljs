(ns core
  (:require [com.rpl.specter :refer [setval]]))

(def init
  "init")

(def sync
  "sync")

(def keydown
  "keydown")

(defn eval-path-setval
  "Sets the value `aval` at the specified path `apath` within the `structure`.
   The path is dynamically evaluated, allowing for runtime determination of where
   the value should be set."
  [apath aval structure]
  (setval apath aval structure))
