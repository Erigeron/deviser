
(ns app.comp.text-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [app.style :as style]
            [app.comp.color-picker :refer [comp-color-picker]]
            [respo-alerts.core :refer [comp-prompt]]))

(defcomp
 comp-text-inspector
 (states content color)
 (div
  {:style {}}
  (div {:style style/area-heading} (<> "Text"))
  (div
   {}
   (comp-prompt
    (>> states :content)
    {:trigger (<> (or content "nothing")), :text "New content", :initial content}
    (fn [result d! m!] (d! :element/content result))))
  (=< nil 8)
  (div
   {}
   (div
    {}
    (<> "color: ")
    (comp-color-picker
     (>> states :color-picker)
     color
     (fn [color d!] (d! :element/change-style [:color color])))))))
