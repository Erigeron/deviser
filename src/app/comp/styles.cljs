
(ns app.comp.styles
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button input span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [log!]]
            [app.style :as style]
            [clojure.string :as s]))

(defcomp
 comp-styles
 (states styles)
 (let [state (or (:data states) "")]
   (div
    {}
    (div {} (<> "Styles"))
    (list-> :div {} (->> styles (map (fn [[k v]] [k (div {} (<> (str k ": " v)))]))))
    (div
     {}
     (input
      {:style style/input,
       :value state,
       :placeholder "property:value",
       :on-input (fn [e d! m!] (m! (:value e)))})
     (=< 8 nil)
     (button
      {:style style/button,
       :on-click (fn [e d! m!]
         (if (not (s/blank? state))
           (let [code state
                 [k v] (map s/trim (s/split code ":"))
                 new-styles (if (s/blank? v) (dissoc styles k) (assoc styles k v))]
             (log! new-styles)
             (d! :element/styles new-styles)
             (m! nil))))}
      (<> "submit"))))))
