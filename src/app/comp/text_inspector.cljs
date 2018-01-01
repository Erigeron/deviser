
(ns app.comp.text-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]
            [app.comp.color-picker :refer [comp-color-picker]]))

(defcomp
 comp-text-inspector
 (states content color)
 (div
  {:style {:border "1px solid black"}}
  (div {} (<> "text inspector"))
  (div
   {}
   (textarea
    {:style style/textarea,
     :value content,
     :placeholder "text content",
     :on-input (fn [e d! m!] (d! :element/content (:value e)))}))
  (div
   {}
   (div
    {}
    (<> "color:")
    (cursor->
     :color-picker
     comp-color-picker
     states
     color
     (fn [color d!] (d! :element/change-style [:color color])))))))
