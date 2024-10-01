(ns background
  (:require [com.rpl.specter :refer [ATOM setval]]
            [lambdaisland.fetch :as fetch]
            [lambdaisland.uri :refer [query-map]]
            [shadow.cljs.modern :refer [js-await]]))

(def state (atom {}))

(defn eval-path-setval
  "Sets the value `aval` at the specified path `apath` within the `structure`.
   The path is dynamically evaluated, allowing for runtime determination of where
   the value should be set."
  [apath aval structure]
  (setval apath aval structure))

(defn init
  []
  (js/console.log "Background script initialized")
  (js/chrome.runtime.onConnect.addListener (fn [port]
                                             (when-let [quest (@state port.sender.tab.id)]
                                               (js-await [response (fetch/get quest)]
                                                         (port.postMessage (:body response)))
                                               (js/chrome.windows.create (clj->js {:url "http://localhost:8000/question.html"
                                                                                   :type "popup"})))))
  (js/chrome.webNavigation.onCommitted.addListener (fn [details]
                                                     (when-let [quest (:quest (query-map details.url))]
                                                       (js/console.log "URL with quest query committed")
                                                       (eval-path-setval [ATOM details.tabId] quest state)))))
