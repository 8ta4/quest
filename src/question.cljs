(ns question
  (:require ["@mui/material/List" :default List]
            [lambdaisland.uri :refer [query-map]]
            [reagent.dom.client :as client]))

(def id
  (int (:id (query-map js/location.href))))

(defn questions
  [{:keys [qa]}]
  [:> List (map (comp (partial vector :li)
                      :question)
                qa)])

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