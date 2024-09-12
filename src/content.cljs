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

(defn match-node
  [{:keys [end answer text matched]}]
  (cond (empty? answer) end
        (empty? text) answer
        (str/blank? (first text)) (if matched
                                    (recur {:current (inc end)
                                            :answer answer
                                            :text (rest text)
                                            :matched true}))
        (= (first answer) (first text)) (recur {:current (inc end)
                                                :answer (rest answer)
                                                :text (rest text)
                                                :matched true})))

(defn collect-nodes*
  [nodes walker]
  (if-let [node (.nextNode walker)]
    (recur (conj nodes node) walker)
    nodes))

(defn collect-nodes
  []
  (collect-nodes* [] (js/document.createTreeWalker js/document.body js/NodeFilter.SHOW_TEXT)))

(defn match-nodes*
  [{:keys [vector-start text-start vector-end text-end text-vector complete-answer unmatched-answer]}])

(defn get-first-answer
  []
  (->> @state
       :qa
       first
       :answer
       remove-blanks))

(defn match-nodes
  []
  (match-nodes* {:vector-start 0
                 :text-start 0
                 :vector-end 0
                 :text-end 0
                 :text-vector (map #(.-nodeValue %) (collect-nodes))
                 :complete-answer (get-first-answer)
                 :unmatched-answer (get-first-answer)}))

(defn init
  []
  (when quest
    (js-await [response (js/browser.runtime.sendMessage quest)]
              (js/console.log "Received response from background script")
              (reset! state {:qa (js->clj (parse response) {:keywordize-keys true})
                             :index 0})
              (match-nodes))))
