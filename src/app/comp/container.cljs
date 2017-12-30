
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [app.comp.layer-tree :refer [comp-layer-tree]]
            [app.comp.previewer :refer [comp-previewer]]
            [app.comp.properties :refer [comp-properties]]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   (div
    {:style (merge ui/global ui/row)}
    (comp-layer-tree)
    (comp-previewer)
    (comp-properties)
    (cursor-> :reel comp-reel states reel {}))))
