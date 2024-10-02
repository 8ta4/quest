(ns question
  (:require ["@mui/material/List" :default List]
            ["@mui/material/ListItemButton" :default ListItemButton]
            [reagent.dom.client :as client]))

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
  (js/console.log "Initializing the question module")
  (.onMessage.addListener (js/chrome.runtime.connect)
                          (fn [message]
                            (js/console.log "Received message from background script")
                            (client/render root
                                           [questions (js->clj message
                                                               {:keywordize-keys true})]))))