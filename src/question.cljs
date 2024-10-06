(ns question
  (:require ["@mui/material/List" :default List]
            ["@mui/material/ListItemButton" :default ListItemButton]
            [answer]
            [core]
            [reagent.dom.client :as client]))

(defonce port
  (js/chrome.runtime.connect))

(defn questions
  [state]
  [:> List
   (map-indexed (fn [index {:keys [question answer]}]
                  ^{:key answer} [:> ListItemButton
                                  {:selected (= index (:id state))}
                                  question])
                (:qa state))])

(defonce root
  (client/create-root (js/document.getElementById "app")))

(defn handle
  [event]
  (js/console.log "Key down event detected:")
  (js/console.log event.key)
  (when (answer/shortcuts event.key)
    (.preventDefault event)
    (.postMessage port (clj->js {:action core/keydown
                                 :data event.key}))))

(defonce state
  (atom nil))

(defn after-load
  []
  (client/render root [questions (js->clj @state {:keywordize-keys true})])
  (set! js/document.onkeydown handle))

(defn init
  []
  (js/console.log "Initializing the question module")
  (.onMessage.addListener port
                          (fn [message]
                            (js/console.log "Received message from background script")
                            (reset! state (js->clj message {:keywordize-keys true}))
                            (after-load))))