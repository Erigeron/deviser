
(ns app.comp.layer-tree
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> action-> list-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [app.style :as style]))

(defn render-action [guide action]
  (button
   {:style (merge
            ui/button
            {:font-size 10,
             :line-height "20px",
             :margin-right 8,
             :background-color (hsl 0 0 97),
             :min-width 40,
             :color (hsl 240 60 70),
             :padding "0 4px"}),
    :on-click (fn [e d! m!] (d! action nil))}
   (<> guide)))

(defcomp
 comp-toolbar
 (focus)
 (div
  {:style {:padding 0, :margin-left 8, :display :inline-block}}
  (render-action "Append" :element/append)
  (render-action "Prepend" :element/prepend)
  (render-action "After" :element/after)
  (render-action "Before" :element/before)
  (render-action "del" :element/remove)))

(defn render-box [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 240 80 98),
             :padding "0 8px",
             :margin "0px 0px",
             :color (hsl 240 80 60)}),
    :on-click (action-> :session/focus path)}
   (<> (:layout tree))))

(defn render-icon [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 94),
             :padding "0 8px",
             :margin "0",
             :color (hsl 0 0 80)}),
    :on-click (action-> :session/focus path)}
   (<> (str "icon"))))

(defn render-space [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 120 60 80), :padding "0 8px", :margin "0px"}),
    :on-click (action-> :session/focus path)}
   (<> "space")))

(defn render-text [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 94),
             :padding "0 8px",
             :margin "0px 0px",
             :color (hsl 0 0 40)}),
    :on-click (action-> :session/focus path)}
   (<> (str "<>" (:content tree)))))

(defn render-element [tree focus path]
  (let [focused? (= focus path)]
    (div
     {:style (merge
              {:border-left (str "1px solid " (if focused? (hsl 60 90 30) (hsl 60 90 90)))})}
     (let [kind (:kind tree)] )
     (case (:kind tree)
       :box (render-box tree path)
       :text (render-text tree path)
       :icon (render-icon tree path)
       :space (render-space tree path)
       "Unknown")
     (if focused? (comp-toolbar focus))
     (list->
      :div
      {:style {:padding "0 0 0 24px"}}
      (->> (:children tree)
           (map (fn [[k v]] [k (render-element v focus (conj path k))]))
           (sort-by first))))))

(defcomp
 comp-layer-tree
 (tree focus)
 (div
  {:style (merge ui/flex ui/column {:background-color (hsl 0 0 100)})}
  (=< nil 8)
  (div
   {:style (merge ui/flex {:overflow :auto, :padding "4px 8px"})}
   (render-element tree focus []))))
