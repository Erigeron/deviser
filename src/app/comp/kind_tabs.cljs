
(ns app.comp.kind-tabs
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> list-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [feather.core :refer [comp-i]]))

(defcomp
 comp-kind-tabs
 (states kind has-children?)
 (div
  {:style ui/row}
  (<> "Node Type:" {:color (hsl 0 0 80)})
  (=< 8 nil)
  (if has-children?
    (div {:style {}} (<> "box"))
    (let [cursor (:cursor states)
          state (or (:data states) {:show-menu? false})
          node-types (list :box :text :icon :space)]
      (div
       {:style {:position :relative}}
       (div
        {:on-click (fn [e d!] (d! cursor (update state :show-menu? not))),
         :style {:cursor :pointer}}
        (<> (name kind))
        (=< 8 nil)
        (comp-i :chevron-down 14 (hsl 200 80 70)))
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
                     :on-click (fn [e d!] (d! :element/set-kind x))}
                    (<> (name x)))]))))))))))
