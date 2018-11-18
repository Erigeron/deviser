
(ns app.comp.presets
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> list-> <> div button textarea span action->]]
            [app.style :as style]))

(def default-presets (list :flex :button :link))

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
                        {:background-color (hsl 200 80 70), :color :white}
                        {})),
              :on-click (action->
                         :element/presets
                         (if (contains? presets preset)
                           (disj presets preset)
                           (conj presets preset)))}
             (<> (name preset)))]))))))
