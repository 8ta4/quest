(ns question
  (:require [lambdaisland.uri :refer [query-map]]
            [reagent.dom :as rdom]))

(def id
  (int (:id (query-map js/location.href))))

(defn init
  []
  (js/console.log "Question script initialized")
  (.onMessage.addListener (js/chrome.tabs.connect id) (fn [message]
                                                        (js->clj message {:keywordize-keys true}))))