
(ns app.comp.home
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp cursor-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [app.comp.layer-tree :refer [comp-layer-tree]]
            [app.comp.inspector :refer [comp-inspector]]
            [app.client-util :refer [wrap-path]]))

(defcomp
 comp-home
 (states store)
 (div
  {:style (merge ui/flex ui/row)}
  (comp-layer-tree (:tree store) (:focus store) (:focuses store))
  (comp-inspector states (get-in (:tree store) (wrap-path (:focus store))))))
