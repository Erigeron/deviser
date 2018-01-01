
(ns app.comp.presets
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]))

(defcomp comp-presets (presets) (div {} (div {} (<> "Presets")) (list-> :div {} (list))))
