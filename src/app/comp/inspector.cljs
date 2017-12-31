
(ns app.comp.inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.comp.kind-tabs :refer [comp-kind-tabs]]
            [app.comp.box-inspector :refer [comp-box-inspector]]
            [app.comp.text-inspector :refer [comp-text-inspector]]
            [app.comp.icon-inspector :refer [comp-icon-inspector]]
            [app.comp.space-inspector :refer [comp-space-inspector]]))

(defcomp
 comp-inspector
 (element)
 (div
  {:style ui/flex}
  (comp-kind-tabs (:kind element))
  (case (:kind element)
    :box (comp-box-inspector (:layout element))
    :icon (comp-icon-inspector)
    :text (comp-text-inspector (:content element))
    :space (comp-space-inspector)
    (<> "Unknown"))))
