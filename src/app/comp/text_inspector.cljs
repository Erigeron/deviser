
(ns app.comp.text-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]
            [app.comp.color-picker :refer [comp-color-picker]]
            [respo-alerts.comp.alerts :refer [comp-prompt]]))

(defcomp
 comp-text-inspector
 (states content color)
 (div
  {:style {}}
  (div {:style style/area-heading} (<> "Text"))
  (div
   {}
   (cursor->
    :content
    comp-prompt
    states
    {:trigger (<> content), :text "New content", :initial content}
    (fn [result d! m!] (d! :element/content result))))
  (=< nil 8)
  (div
   {}
   (div
    {}
    (<> "color: ")
    (cursor->
     :color-picker
     comp-color-picker
     states
     color
     (fn [color d!] (d! :element/change-style [:color color])))))))
