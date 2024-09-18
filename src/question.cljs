(ns question
  (:require [lambdaisland.uri :refer [query-map]]
            [reagent.dom :as rdom]
            ["@mui/material/List" :default List]))

(def id
  (int (:id (query-map js/location.href))))

(defn questions
  [{:keys [qa]}]
  [:> List (map (comp (partial vector :li)
                      :question)
                qa)])

(defn init
  []
  (js/console.log "Question script initialized")
  (.onMessage.addListener (js/chrome.tabs.connect id)
                          (fn [message]
                            (rdom/render [questions (js->clj message {:keywordize-keys true})]
                                         (js/document.getElementById "app")))))