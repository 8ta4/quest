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

(defn collect-nodes
  [nodes walker]
  (if-let [node (.nextNode walker)]
    (recur (conj nodes node) walker)
    nodes))

(def nodes
  (collect-nodes [] (js/document.createTreeWalker js/document.body js/NodeFilter.SHOW_TEXT)))

(def text-sequence
  (map #(.-nodeValue %) nodes))

(defn match-nodes
  [{:keys [sequence-start text-start sequence-end text-end complete-answer unmatched-answer] :as context}]
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
    (.setStart range* node (or start 0))
    (.setEnd range* node (or end (count node.nodeValue)))
    (set! span.id id)
    (set! span.style "visibility: hidden !important")
    (.surroundContents range* span)))

(defn wrap-nodes
  [{:keys [sequence-start text-start sequence-end text-end id]}]
  (if (= sequence-start sequence-end)
    (wrap-node (nth nodes sequence-start) text-start text-end id)
    (do (wrap-node (nth nodes sequence-start) text-start nil id)
        (run! #(wrap-node % nil nil id) (subvec nodes (inc sequence-start) sequence-end))
        (wrap-node (nth nodes sequence-end) nil text-end id))))

(defn get-answers
  []
  (map (comp remove-blanks :answer) (:qa @state)))

(defn process-nodes
  []
  (reduce (fn [{:keys [sequence-start text-start sequence-end text-end] :as context} [id answer]]
            (let [result (match-nodes (merge context
                                             {:sequence-start sequence-end
                                              :text-start text-end
                                              :complete-answer answer
                                              :unmatched-answer answer}))]
              (wrap-nodes (setval :id id (if (zero? id)
                                           result
                                           (merge result
                                                  {:sequence-start sequence-start
                                                   :text-start text-start}))))
              result))
          {:sequence-start 0
           :text-start 0
           :sequence-end 0
           :text-end 0}
          (map-indexed vector (get-answers))))

(defn init
  []
  (when quest
    (js-await [response (js/browser.runtime.sendMessage quest)]
              (js/console.log "Received response from background script")
              (reset! state {:qa (js->clj (parse response) {:keywordize-keys true})
                             :index 0})
              (process-nodes))))
