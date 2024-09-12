(ns content
  (:require [clojure.string :as str]
            [lambdaisland.uri :refer [query-map]]
            [shadow.cljs.modern :refer [js-await]]
            [yaml :refer [parse]]))

(defonce state
  (atom {}))

(def quest
  (:quest (query-map js/location.href)))

(def remove-blanks
  (partial remove str/blank?))

(defn match
  [current answer text matched]
  (cond (empty? answer) current
        (empty? text) answer
        (str/blank? (first text)) (if matched
                                    (recur (inc current) answer (rest text) true))
        (= (first answer) (first text)) (recur (inc current) (rest answer) (rest text) true)))

(defn collect-nodes
  [nodes walker]
  (if-let [node (.nextNode walker)]
    (recur (conj nodes node) walker)
    nodes))

(defn init
  []
  (when quest
    (js-await [response (js/browser.runtime.sendMessage quest)]
              (js/console.log "Received response from background script")
              (reset! state {:qa (js->clj (parse response) {:keywordize-keys true})
                             :index 0})
              (->> @state
                   :qa
                   first
                   :answer
                   remove-blanks)
              (->> (js/document.createTreeWalker js/document.body js/NodeFilter.SHOW_TEXT)
                   (collect-nodes [])
                   (map #(.-nodeValue %))))))
