
(ns app.comp.box-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> list-> <> div button textarea span]]
            [app.style :as style]
            [app.comp.color-picker :refer [comp-color-picker]]))

(def layouts [:row :column :center :row-center :row-middle :row-parted :column-parted])

(defcomp
 comp-box-inspector
 (states current-layout color)
 (div
  {}
  (div {:style style/area-heading} (<> "Layout Picker"))
  (list->
   :div
   {}
   (->> layouts
        (map
         (fn [layout]
           [layout
            (div
             {:style (merge
                      style/button
                      (if (= current-layout layout)
                        {:background-color (hsl 200 80 70), :color :white})),
              :on-click (fn [e d! m!] (d! :element/layout layout))}
             (<> (name layout)))]))))
  (div
   {}
   (div
    {}
    (<> "background-color: ")
    (comp-color-picker
     (>> states :color-picker)
     color
     (fn [new-color d!] (d! :element/change-style [:background-color new-color])))))))
