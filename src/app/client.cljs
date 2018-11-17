
(ns app.client
  (:require [respo.core :refer [render! clear-cache! realize-ssr!]]
            [respo.cursor :refer [mutate]]
            [app.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [app.connection :refer [send! setup-socket!]]
            [app.config :as config]
            ("url-parse" :as parse)))

(declare dispatch!)

(declare try-preview!)

(declare connect!)

(declare simulate-login!)

(defonce *states (atom {}))

(defonce *store (atom nil))

(defn try-preview! []
  (let [initial-page (-> (parse js/location.href true) .-query .-page)]
    (if (= initial-page "preview") (dispatch! :router/change {:name :preview}))))

(defn simulate-login! []
  (let [raw (.getItem js/localStorage (:storage-key config/site))]
    (if (some? raw)
      (do (println "Found storage.") (dispatch! :user/log-in (read-string raw)))
      (do (println "Found no storage.")))))

(defn dispatch! [op op-data]
  (case op
    :states (reset! *states ((mutate op-data) @*states))
    :effect/connect (connect! try-preview!)
    (do (println "Dispatch" op op-data) (send! op op-data))))

(defn connect! [cb!]
  (setup-socket!
   *store
   {:url (str "ws://" (.-hostname js/location) ":" (:port config/site)),
    :on-close! (fn [event] (reset! *store nil) (.error js/console "Lost connection!")),
    :on-open! (fn [event] (simulate-login!) (cb!))}))

(def mount-target (.querySelector js/document ".app"))

(defn render-app! [renderer]
  (renderer mount-target (comp-container @*states @*store) #(dispatch! %1 %2)))

(def ssr? (some? (.querySelector js/document "meta.respo-ssr")))

(defn main! []
  (if ssr? (render-app! realize-ssr!))
  (render-app! render!)
  (connect! try-preview!)
  (add-watch *store :changes #(render-app! render!))
  (add-watch *states :changes #(render-app! render!))
  (println "App started!"))

(defn reload! [] (clear-cache!) (render-app! render!) (println "Code updated."))
