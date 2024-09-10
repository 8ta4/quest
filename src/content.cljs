(ns content
  (:require [clojure.string :as str]
            [lambdaisland.uri :refer [query-map]]
            [shadow.cljs.modern :refer [js-await]]
            [yaml :refer [parse]]))

(defonce state
  (atom {}))

(def quest
  (:quest (query-map js/location.href)))

(def walker
  (js/document.createTreeWalker js/document js/NodeFilter.SHOW_TEXT))

(def remove-blanks
  (partial remove str/blank?))

(defn traverse-nodes
  [nodes start current answer]
  (subs (.-currentNode.nodeValue walker) current))

(defn init
  []
  (.nextNode walker)
  (when quest
    (js-await [response (js/browser.runtime.sendMessage quest)]
              (js/console.log "Received response from background script")
              (reset! state {:qa (js->clj (parse response) {:keywordize-keys true})
                             :index 0})
              (->> @state
                   :qa
                   first
                   :answer
                   remove-blanks
                   (traverse-nodes [] 0 0)))))