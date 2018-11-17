
(ns app.comp.kind-tabs
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> list-> <> div button textarea span mutation->]]
            [respo.comp.space :refer [=<]]
            [respo-ui.comp.icon :refer [comp-icon]]))

(defcomp
 comp-kind-tabs
 (states kind has-children?)
 (div
  {:style ui/row}
  (<> "Node Type:" {:color (hsl 0 0 80)})
  (=< 8 nil)
  (if has-children?
    (div {:style {}} (<> "box"))
    (let [state (or (:data states) {:show-menu? false})
          node-types (list :box :text :icon :space)]
      (div
       {:style {:position :relative}}
       (div
        {:on-click (mutation-> (update state :show-menu? not)), :style {:cursor :pointer}}
        (<> (name kind))
        (=< 8 nil)
        (comp-icon :android-arrow-dropdown))
       (if (:show-menu? state)
         (list->
          :div
          {:style (merge
                   ui/column
                   {:top "100%",
                    :position :absolute,
                    :background-color :white,
                    :border (str "1px solid " (hsl 0 0 86)),
                    :z-index 99})}
          (->> node-types
               (map
                (fn [x]
                  [x
                   (div
                    {:style (merge
                             ui/flex
                             {:cursor :pointer, :padding "0 8px", :color (hsl 0 0 70)}
                             (if (= x kind) {:color (hsl 0 0 30)})),
                     :on-click (fn [e d! m!] (d! :element/set-kind x))}
                    (<> (name x)))]))))))))))
