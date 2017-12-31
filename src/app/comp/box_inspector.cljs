
(ns app.comp.box-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(def layouts [:row :column :center :row-center :row-parted :column-parted])

(defcomp
 comp-box-inspector
 (current-layout)
 (div
  {}
  (div {} (<> "Layout Picker"))
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
                      {:background-color (if (= current-layout layout)
                         (hsl 240 90 80)
                         (hsl 240 60 90))}),
              :on-click (fn [e d! m!] (d! :element/layout layout))}
             (<> (name layout)))]))))))
