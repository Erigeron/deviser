
(ns app.comp.presets
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(def default-presets (list :flex :button :clickable-text))

(defcomp
 comp-presets
 (presets)
 (div
  {}
  (div {:style style/area-heading} (<> "Presets"))
  (list->
   :div
   {}
   (->> default-presets
        (map
         (fn [preset]
           [preset
            (div
             {:style (merge
                      style/button
                      (if (contains? presets preset)
                        {}
                        {:background-color :white,
                         :border (str "1px solid " (hsl 0 0 70)),
                         :color (hsl 0 0 70)})),
              :on-click (fn [e d! m!]
                (d!
                 :element/presets
                 (if (contains? presets preset) (disj presets preset) (conj presets preset))))}
             (<> (name preset)))]))))))
