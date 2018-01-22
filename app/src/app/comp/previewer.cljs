
(ns app.comp.previewer
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [log!]]
            [respo.core :refer [create-element]]
            [app.style :as style]))

(declare render-box)

(declare render-tree)

(defn expand-presets [presets]
  (->> presets
       (map
        (fn [preset]
          (case preset
            :flex ui/flex
            :fullscreen ui/fullscreen
            :button ui/button
            :clickable-text ui/clickable-text
            {:background-color :red})))
       (apply merge)))

(def style-focused {:outline (str "1px solid red")})

(defn render-icon [element path focused?]
  (span
   {:style (if focused? style-focused),
    :inner-text "this is an icon",
    :on-click (fn [e d! m!] (d! :focus path))}))

(defn render-space [element path focused?]
  (span
   {:style (merge
            {:min-width 1, :min-height 1}
            (:styles element)
            (if focused? style-focused)),
    :on-click (fn [e d! m!] (d! :focus path))}))

(defn render-text [tree path focused?]
  (span
   {:style (merge
            (expand-presets (:presets tree))
            (:styles tree)
            (if focused? style-focused)),
    :inner-text (:content tree),
    :on-click (fn [e d! m!] (d! :focus path))}))

(defn render-tree [tree focus path]
  (case (:kind tree)
    :box (render-box tree focus path)
    :text (render-text tree path (= focus path))
    :icon (render-icon tree path (= focus path))
    :space (render-space tree path (= focus path))
    (<> "Unknown")))

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
            (if (= focus path) style-focused)),
    :on-click (fn [e d! m!] (d! :focus path))}
   (->> (:children tree)
        (map (fn [[k v]] [k (render-tree v focus (conj path k))]))
        (sort-by first))))

(defcomp
 comp-previewer
 (tree focus)
 (div
  {:style (merge ui/global ui/fullscreen {:cursor :default, :background-color :white})}
  (render-tree tree focus [])
  (div
   {:style {:position :absolute,
            :right 8,
            :top 8,
            :font-weight 100,
            :font-family style/font-fancy,
            :cursor :pointer},
    :on-click (fn [e d! m!] (d! :router/change {:name :home, :data nil}))}
   (<> "Close"))))
