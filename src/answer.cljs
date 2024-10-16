(ns answer
  (:require [clojure.string :as str]
            [com.rpl.specter :refer [ALL ATOM END NONE nthpath setval
                                     transform]]
            [core]
            [yaml :refer [parse]]))

(defonce state
  (atom {}))

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
                                            :matched true})
                                    nil)
        (= (first answer) (first text)) (recur {:end (inc end)
                                                :answer (rest answer)
                                                :text (rest text)
                                                :matched true})))

(defn collect-nodes
  [nodes walker]
  (if-let [node (.nextNode walker)]
    (recur (conj nodes node) walker)
    nodes))

(defn collect-text-nodes
  []
  (collect-nodes [] (js/document.createTreeWalker js/document.body js/NodeFilter.SHOW_TEXT)))

(defn collect-text-sequence
  []
  (map #(.-nodeValue %) (collect-text-nodes)))

(defn match-nodes
  [{:keys [text-sequence sequence-start text-start sequence-end text-end complete-answer unmatched-answer] :as context}]
  (if (empty? unmatched-answer)
    context
    (let [result (match-node {:end text-end
                              :answer unmatched-answer
                              :text (subs (nth text-sequence sequence-end) text-end)
                              :matched (not= complete-answer unmatched-answer)})]
      (cond (integer? result) (setval :text-end result context)
            (seq? result) (recur (merge context {:text-end 0
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

(def style
  "visibility: hidden !important")

(defn wrap-node
  [nodes index start end id]
  (let [range* (js/document.createRange)
        node (nth nodes index)
        span (js/document.createElement "span")]
    (.setStart range* node (or start 0))
    (.setEnd range* node (or end (count node.nodeValue)))
    (set! span.className id)
    (set! span.style style)
    (.surroundContents range* span)))

(defn build-segment
  [{:keys [sequence-start text-start sequence-end text-end id]}]
  (if (= sequence-start sequence-end)
    [[sequence-start text-start text-end id]]
    (concat [[sequence-start text-start nil id]]
            (map #(vector % nil nil id) (range (inc sequence-start) sequence-end))
            [[sequence-end nil text-end id]])))

(defn get-answers
  []
  (map (comp remove-blanks :answer) (:qa @state)))

(defn build-segments
  []
  (->> (get-answers)
       (map-indexed vector)
       (reduce (fn [{:keys [sequence-end text-end] :as context} [id answer]]
                 (let [result (match-nodes (merge context
                                                  {:sequence-start sequence-end
                                                   :text-start text-end
                                                   :complete-answer answer
                                                   :unmatched-answer answer}))]

                   (setval [:segments END]
                           (build-segment (setval :id
                                                  id
                                                  (if (zero? id)
                                                    result
                                                    (merge result
                                                           {:sequence-start sequence-end
                                                            :text-start text-end}))))
                           result)))
               {:text-sequence (collect-text-sequence)
                :segments []
                :sequence-start 0
                :text-start 0
                :sequence-end 0
                :text-end 0})
       :segments
;; Reversing the segments is necessary because if we add spans in the original order,
;; the offsets get messed up. This happens because adding a span modifies the DOM,
;; which shifts the positions of subsequent text nodes. By reversing the segments,
;; we ensure that spans are added from the end to the beginning, avoiding this issue.
       reverse))

(defn process-nodes
  []
  (run! (partial apply wrap-node (collect-text-nodes)) (build-segments)))

(defn move-to-next
  []
  (transform [ATOM :id]
             #(-> @state
                  :qa
                  count
                  dec
                  (min (inc %)))
             state))

(defn move-to-previous
  []
  (transform [ATOM :id] #(max 0 (dec %)) state))

(defn set-visibility
  [bar element]
  (set! (.-style element) (if bar
                            ""
                            style)))

(defn answer [response]
  (let [selected (nth (:qa @state) (:id @state))
        selected* (if (= (:response selected) response)
                    (setval :visible false (setval :response NONE selected))
                    (merge selected {:response response
                                     :visible true}))
        elements (js->clj (js/document.getElementsByClassName (:id @state)))]
    (run! (partial set-visibility (:visible selected*)) elements)
    (when-not (:visible selected)
      (when-let [element (first (remove (comp str/blank?
                                              #(.-innerText %))
                                        elements))]
        (.scrollIntoView element)))
    (core/eval-path-setval [ATOM :qa (nthpath (:id @state))] selected* state)))

(def shortcuts
  {"ArrowRight" #(answer true)
   "ArrowLeft" #(answer false)
   "ArrowDown" move-to-next
   "ArrowUp" move-to-previous})

(defn handle
  [event]
  (js/console.log "Key down event detected:")
  (js/console.log event.key)
  (when-let [command (shortcuts event.key)]
    (.preventDefault event)
    (command)))

(when js/goog.DEBUG
  (defonce body (atom nil)))

(defn after-load
  []
;; In development mode, the document body is replaced with a cloned version to enable hot reloading.
;; In production, the body content could have event listeners attached.
;; Cloning the body may result in the loss of those event listeners.
  (when js/goog.DEBUG
    (js/document.body.replaceWith (.cloneNode @body true)))
  (process-nodes)
  (set! js/document.onkeydown handle))

(defn init
  []
  (.onMessage.addListener (js/chrome.runtime.connect)
                          (fn [message sender]
                            (js/console.log "Received message from background script")
                            (let [message* (js->clj message {:keywordize-keys true})]
                              (({core/init #(do (reset! state
                                                        {:qa (setval [ALL :visible]
                                                                     false
                                                                     (-> message*
                                                                         :data
                                                                         parse
                                                                         (js->clj {:keywordize-keys true})))
                                                         :id 0})
                                                (add-watch state
                                                           :change
                                                           (fn [_ _ _ new-state]
                                                             (.postMessage sender (clj->js new-state))))
                                                (when js/goog.DEBUG
                                                  (reset! body (js/document.body.cloneNode true)))
                                                (after-load))
                                 core/sync #(.postMessage sender (clj->js @state))
                                 core/keydown #(when-let [command (shortcuts (:data message*))]
                                                 (command))}
                                (:action message*)))))))
