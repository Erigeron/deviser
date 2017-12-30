
(ns app.comp.layer-tree
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]))

(defcomp comp-layer-tree () (div {:style ui/flex} (<> "layer-tree")))
