
(ns app.comp.styles
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> list-> <> div button input span]]
            [respo.comp.space :refer [=<]]
            [app.style :as style]
            [clojure.string :as s]
            [respo-alerts.core :refer [comp-prompt]]))

(defn parse-styles [code styles]
  (let [[k v] (map s/trim (s/split code ":"))]
    (if (s/blank? v) (dissoc styles (keyword k)) (assoc styles (keyword k) v))))

(defcomp
 comp-styles
 (states styles)
 (div
  {}
  (div {:style style/area-heading} (<> "Styles"))
  (list->
   {:style {:font-family "Menlo,monospace", :font-size 12, :line-height "20px"}}
   (->> styles
        (map
         (fn [[k v]]
           [k
            (comp-prompt
             (>> states k)
             {:trigger (div {} (<> (str (name k) ": " v))),
              :text "Edit style:",
              :initial v,
              :style {:display :block}}
             (fn [result d!] (d! :element/styles (assoc styles k result))))]))))
  (comp-prompt
   (>> states :add)
   {:trigger (button {:style style/button, :inner-text "Set"}),
    :text "New styles in `property:value`:",
    :style {:display :block}}
   (fn [result d!] (d! :element/styles (parse-styles result styles))))))
