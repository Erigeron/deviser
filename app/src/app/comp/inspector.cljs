
(ns app.comp.inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [log!]]
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
 (log! (pr-str states))
 (div
  {:style ui/flex}
  (comp-kind-tabs (:kind element) (not (empty? (:children element))))
  (div
   {:style {:padding 8}}
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
   (cursor-> :styles comp-styles states (:styles element)))))
