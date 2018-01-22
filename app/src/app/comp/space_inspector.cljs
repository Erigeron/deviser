
(ns app.comp.space-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.comp.int-control :refer [comp-int-control]]))

(defcomp
 comp-space-inspector
 (styles)
 (div
  {}
  (<> "space inspector")
  (div
   {}
   (div
    {}
    (<> "Width:")
    (=< 8 nil)
    (comp-int-control (:width styles) 4 (fn [x d!] (d! :element/change-style [:width x]))))
   (div
    {}
    (<> "Height:")
    (=< 8 nil)
    (comp-int-control (:height styles) 4 (fn [x d!] (d! :element/change-style [:height x])))))))
