
(ns app.comp.home
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [log!]]
            [respo.comp.space :refer [=<]]
            [app.comp.layer-tree :refer [comp-layer-tree]]
            [app.comp.inspector :refer [comp-inspector]]
            [app.util :refer [wrap-path]]))

(defcomp
 comp-home
 (states store)
 (div
  {:style (merge ui/flex ui/row)}
  (comp-layer-tree (:tree store) (:focus store))
  (comp-inspector states (get-in (:tree store) (wrap-path (:focus store))))))
