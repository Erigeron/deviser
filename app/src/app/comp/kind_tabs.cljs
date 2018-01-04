
(ns app.comp.kind-tabs
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]))

(defcomp
 comp-kind-tabs
 (kind has-children?)
 (list->
  :div
  {:style ui/row}
  (->> (if has-children? (list :box) (list :box :text :icon :space))
       (map
        (fn [x]
          [x
           (div
            {:style (merge
                     ui/flex
                     ui/center
                     {:cursor :pointer}
                     {:border-bottom (str
                                      "1px solid "
                                      (if (= kind x) (hsl 0 0 70) (hsl 0 0 94)))}),
             :on-click (fn [e d! m!] (d! :element/set-kind x))}
            (<> (name x)))])))))
