
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [app.comp.layer-tree :refer [comp-layer-tree]]
            [app.comp.previewer :refer [comp-previewer]]
            [app.comp.inspector :refer [comp-inspector]]
            [app.util :refer [wrap-path]]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   (div
    {:style (merge ui/global ui/fullscreen ui/row)}
    (comp-previewer (:tree store))
    (comp-layer-tree (:tree store) (:focus store))
    (comp-inspector states (get-in (:tree store) (wrap-path (:focus store))))
    (cursor-> :reel comp-reel states reel {:width "40%"}))))
