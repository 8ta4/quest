(ns question
  (:require ["@mui/icons-material/Cancel" :default Cancel]
            ["@mui/icons-material/CheckCircle" :default CheckCircle]
            ["@mui/material/List" :default List]
            ["@mui/material/ListItemButton" :default ListItemButton]
            [answer]
            [core]
            [reagent.dom.client :as client]))

(defonce port
  (js/chrome.runtime.connect))

(defn questions
  [state*]
  [:> List
   (map-indexed (fn [index {:keys [question answer yes response]}]
                  ^{:key answer} [:> ListItemButton
                                  (if (= index (:id state*))
                                    {:ref (fn [element]
                                            (when element
                                              (.scrollIntoView element (clj->js {:block "nearest"}))))
                                     :style {:background-color "lightgray"}}
                                    {})
                                  (cond
                                    (nil? response) [:> CheckCircle
                                                     {:style {:visibility "hidden"}}]
                                    (= yes response) [:> CheckCircle]
                                    :else [:> Cancel])
                                  question])
                (:qa state*))])

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