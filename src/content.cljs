(ns content
  (:require [clojure.string :as str]
            [com.rpl.specter :refer [setval]]
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
                                    (recur {:end (inc end)
                                            :answer answer
                                            :text (rest text)
                                            :matched true}))
        (= (first answer) (first text)) (recur {:end (inc end)
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
  [{:keys [sequence-start text-start sequence-end text-end text-sequence complete-answer unmatched-answer] :as context}]
  (if (empty? unmatched-answer)
    context
    (let [result (match-node {:end text-end
                              :answer unmatched-answer
                              :text (subs (nth text-sequence sequence-start) text-start)
                              :matched (not= complete-answer unmatched-answer)})]
      (cond (integer? result) (setval :text-end result context)
            (string? result) (recur (merge context {:text-end 0
                                                    :sequence-end (inc sequence-end)
                                                    :unmatched-answer result}))
            (< (inc text-start) (count (nth text-sequence sequence-start))) (recur (merge context {:text-start (inc text-start)
                                                                                                   :sequence-end sequence-start
                                                                                                   :text-end (inc text-start)
                                                                                                   :unmatched-answer complete-answer}))
            :else (recur (merge context {:sequence-start (inc sequence-start)
                                         :text-start 0
                                         :sequence-end (inc sequence-start)
                                         :text-end 0
                                         :unmatched-answer complete-answer}))))))

(defn get-first-answer
  []
  (->> @state
       :qa
       first
       :answer
       remove-blanks))

(defn wrap-node
  [node start end id]
  (let [range* (js/document.createRange)
        span (js/document.createElement "span")]
    (.setStart range* node start)
    (.setEnd range* node end)
    (set! span.id id)
    (set! span.style "visibility: hidden !important")
    (.surroundContents range* span)))

(defn wrap-nodes
  [{:keys [nodes sequence-start text-start sequence-end text-end id]}]
  (if (= sequence-start sequence-end)
    (wrap-node (nth nodes sequence-start) text-start text-end id)))

(defn process-nodes
  []
  (wrap-nodes (merge (match-nodes* {:sequence-start 0
                                    :text-start 0
                                    :sequence-end 0
                                    :text-end 0
                                    :text-sequence (map #(.-nodeValue %) (collect-nodes))
                                    :complete-answer (get-first-answer)
                                    :unmatched-answer (get-first-answer)})
                     {:nodes (collect-nodes)
                      :id 0})))

(defn init
  []
  (when quest
    (js-await [response (js/browser.runtime.sendMessage quest)]
              (js/console.log "Received response from background script")
              (reset! state {:qa (js->clj (parse response) {:keywordize-keys true})
                             :index 0})
              (process-nodes))))
