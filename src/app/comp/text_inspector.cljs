
(ns app.comp.text-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]
            [app.comp.color-picker :refer [comp-color-picker]]))

(defcomp
 comp-text-inspector
 (states content color)
 (let [state (or (:data states) {:text ""})]
   (div
    {:style {}}
    (div {:style style/area-heading} (<> "Text"))
    (div {} (<> content))
    (div
     {}
     (textarea
      {:style style/textarea,
       :value (:text state),
       :placeholder "text content",
       :on-input (fn [e d! m!] (m! (assoc state :text (:value e))))})
     (button
      {:style style/button, :on-click (fn [e d! m!] (d! :element/content (:text state)))}
      (<> "change")))
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
       (fn [color d!] (d! :element/change-style [:color color]))))))))
