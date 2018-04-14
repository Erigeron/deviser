
(ns app.comp.styles
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button input span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [log!]]
            [app.style :as style]
            [clojure.string :as s]
            [keycode.core :as keycode]))

(defcomp
 comp-styles
 (states styles)
 (let [state (or (:data states) "")]
   (div
    {}
    (div {:style style/area-heading} (<> "Styles"))
    (list->
     {:style {:font-family "Menlo,monospace", :font-size 12, :line-height "20px"}}
     (->> styles (map (fn [[k v]] [k (div {} (<> (str (name k) ": " v)))]))))
    (div
     {}
     (input
      {:style style/input,
       :value state,
       :placeholder "property:value",
       :on-input (fn [e d! m!] (m! (:value e))),
       :on-keydown (fn [e d! m!]
         (if (and (= keycode/return (:key-code e)) (not (s/blank? state)))
           (let [code state
                 [k v] (map s/trim (s/split code ":"))
                 new-styles (if (s/blank? v)
                              (dissoc styles (keyword k))
                              (assoc styles (keyword k) v))]
             (log! new-styles)
             (d! :element/styles new-styles)
             (m! nil))))})))))
