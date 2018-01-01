
(ns app.comp.previewer
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [log!]]))

(declare render-tree)

(declare render-box)

(def style-focused {:outline (str "1px solid red")})

(defn render-space [element focused?]
  (span
   {:style (merge
            {:min-width 1, :min-height 1}
            (:styles element)
            (if focused? style-focused))}))

(defn render-icon [element focused?] (<> "this is an icon" (if focused? style-focused)))

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

(defn render-text [tree focused?]
  (<>
   (:content tree)
   (merge (expand-presets (:presets tree)) (:styles tree) (if focused? style-focused))))

(defn render-box [tree focus path]
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
            (:styles tree)
            (if (= focus path) style-focused))}
   (->> (:children tree)
        (map (fn [[k v]] [k (render-tree v focus (conj path k))]))
        (sort-by first))))

(defn render-tree [tree focus path]
  (case (:kind tree)
    :box (render-box tree focus path)
    :text (render-text tree (= focus path))
    :icon (render-icon tree (= focus path))
    :space (render-space tree (= focus path))
    (<> "Unknown")))

(defcomp
 comp-previewer
 (tree focus)
 (div {:style (merge ui/flex {:position :relative})} (render-tree tree focus [])))
