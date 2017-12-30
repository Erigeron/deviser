
(ns app.comp.previewer
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]))

(defcomp comp-previewer () (div {:style ui/flex} (<> "previewer")))
