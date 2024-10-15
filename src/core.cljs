(ns core
  (:require [com.rpl.specter :refer [setval transform]]))

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

(defn eval-path-transform
  "The path (`apath`) is dynamically evaluated, and the `transform-fn` is applied
   to the value at that path within the `structure`. This is useful when the path
   needs to be determined at runtime."
  [apath transform-fn structure]
  (transform apath transform-fn structure))