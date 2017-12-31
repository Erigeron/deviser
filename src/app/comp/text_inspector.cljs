
(ns app.comp.text-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(defcomp
 comp-text-inspector
 (content)
 (div
  {}
  (div {} (<> "text inspector"))
  (div
   {}
   (textarea
    {:style style/textarea,
     :value content,
     :placeholder "text content",
     :on-input (fn [e d! m!] (d! :element/content (:value e)))}))))
