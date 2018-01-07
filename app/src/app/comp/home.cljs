
(ns app.comp.home
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [log!]]
            [respo.comp.space :refer [=<]]
            [app.comp.layer-tree :refer [comp-layer-tree]]
            [app.comp.inspector :refer [comp-inspector]]
            [app.util :refer [wrap-path]]))

(def divider (div {:style {:width 1, :background-color (hsl 0 0 80)}}))

(defcomp
 comp-home
 (states store)
 (div
  {:style (merge ui/flex ui/row)}
  divider
  (comp-layer-tree (:tree store) (:focus store))
  divider
  (comp-inspector states (get-in (:tree store) (wrap-path (:focus store))))))
