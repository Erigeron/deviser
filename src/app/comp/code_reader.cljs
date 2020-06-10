
(ns app.comp.code-reader
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> list-> <> div button textarea span]]
            [app.style :as style]))

(defcomp
 comp-code-reader
 (tree)
 (div
  {}
  (textarea
   {:style (merge style/textarea {:width 800, :height 400, :font-family style/mono}),
    :value (pr-str tree)})))
