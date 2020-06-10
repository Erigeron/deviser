
(ns app.comp.inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> <> div button textarea span]]
            [app.comp.kind-tabs :refer [comp-kind-tabs]]
            [app.comp.box-inspector :refer [comp-box-inspector]]
            [app.comp.text-inspector :refer [comp-text-inspector]]
            [app.comp.icon-inspector :refer [comp-icon-inspector]]
            [app.comp.space-inspector :refer [comp-space-inspector]]
            [app.comp.presets :refer [comp-presets]]
            [app.comp.styles :refer [comp-styles]]))

(defcomp
 comp-inspector
 (states element)
 (div
  {:style ui/flex}
  (comp-kind-tabs (>> states :tabs) (:kind element) (not (empty? (:children element))))
  (div
   {:style {}}
   (case (:kind element)
     :box
       (comp-box-inspector
        states
        (:layout element)
        (get-in element [:styles :background-color]))
     :icon (comp-icon-inspector)
     :text (comp-text-inspector states (:content element) (get-in element [:styles :color]))
     :space (comp-space-inspector (:styles element))
     (<> "Unknown"))
   (comp-presets (:presets element))
   (comp-styles (>> states :styles) (:styles element)))))
