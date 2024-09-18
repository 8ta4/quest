(ns question
  (:require ["@mui/material/List" :default List]
            ["@mui/material/ListItemButton" :default ListItemButton]
            [lambdaisland.uri :refer [query-map]]
            [reagent.dom.client :as client]))

(def id
  (int (:id (query-map js/location.href))))

(defn questions
  [state]
  [:> List
   (map-indexed (fn [index {:keys [question answer]}]
                  ^{:key answer} [:> ListItemButton
                                  {:selected (= index (:id state))}
                                  question])
                (:qa state))])

(def root
  (client/create-root (js/document.getElementById "app")))

(defn init
  []
  (js/console.log "Question script initialized")
  (.onMessage.addListener (js/chrome.tabs.connect id)
                          (fn [message]
                            (client/render root
                                           [questions (js->clj message
                                                               {:keywordize-keys true})]))))