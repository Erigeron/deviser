
(ns app.comp.previewer
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [log!]]))

(declare render-tree)

(declare render-box)

(defn render-space [element]
  (span {:style (merge {:min-width 1, :min-height 1} (:styles element))}))

(defn render-icon [element] (<> "this is an icon"))

(defn expand-presets [presets]
  (->> presets
       (map
        (fn [preset]
          (case preset
            :fullscreen ui/fullscreen
            :button ui/button
            :clickable-text ui/clickable-text
            {:background-color :red})))
       (apply merge)))

(defn render-text [tree]
  (<> (:content tree) (merge (expand-presets (:presets tree)) (:styles tree))))

(defn render-box [tree]
  (list->
   :div
   {:style (merge
            (case (:layout tree)
              :column ui/column
              :row ui/row
              :center ui/center
              :row-center ui/row-center
              :row-parted ui/row-parted
              :column-parted ui/column-parted
              {:background-color :red})
            (expand-presets (:presets tree))
            (:styles tree))}
   (->> (:children tree) (map (fn [[k v]] [k (render-tree v)])) (sort-by first))))

(defn render-tree [tree]
  (case (:kind tree)
    :box (render-box tree)
    :text (render-text tree)
    :icon (render-icon tree)
    :space (render-space tree)
    (<> "Unknown")))

(defcomp
 comp-previewer
 (tree)
 (div {:style (merge ui/flex {:position :relative})} (render-tree tree)))
