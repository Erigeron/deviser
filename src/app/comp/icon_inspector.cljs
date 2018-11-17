
(ns app.comp.icon-inspector
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp cursor-> <> div button textarea span]]))

(defcomp comp-icon-inspector () (div {} (<> "icon inspector")))
