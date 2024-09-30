(ns background
  (:require [com.rpl.specter :refer [ATOM setval]]
            [lambdaisland.uri :refer [query-map]]))

(def state (atom {}))

(defn eval-path-setval
  "Sets the value `aval` at the specified path `apath` within the `structure`.
   The path is dynamically evaluated, allowing for runtime determination of where
   the value should be set."
  [apath aval structure]
  (setval apath aval structure))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.webNavigation.onCommitted.addListener (fn [details]
                                                     (when-let [quest (:quest (query-map details.url))]
                                                       (eval-path-setval [ATOM details.tabId] quest state)))))
