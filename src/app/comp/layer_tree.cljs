
(ns app.comp.layer-tree
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(defn render-action [guide action]
  (button
   {:style (merge style/button {:font-size 12, :line-height "20px", :margin-right 8}),
    :on-click (fn [e d! m!] (d! action nil))}
   (<> guide)))

(defn render-box [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 240 80 80), :padding "0 8px", :margin "0px 0px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (:layout tree))))

(defn render-icon [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 50), :padding "0 8px", :margin "0"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (str "icon"))))

(defn render-space [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 120 60 80), :padding "0 8px", :margin "0px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> "space")))

(defn render-text [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 94),
             :padding "0 8px",
             :margin "0px 0px",
             :color (hsl 0 0 40)}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (str "<>" (:content tree)))))

(defn render-element [tree focus path]
  (div
   {:style (merge
            {:border-left (str
                           "1px solid "
                           (if (= focus path) (hsl 60 90 30) (hsl 60 90 90)))})}
   (let [kind (:kind tree)] )
   (case (:kind tree)
     :box (render-box tree path)
     :text (render-text tree path)
     :icon (render-icon tree path)
     :space (render-space tree path)
     "Unknown")
   (list->
    :div
    {:style {:padding "0 0 0 24px"}}
    (->> (:children tree)
         (map (fn [[k v]] [k (render-element v focus (conj path k))]))
         (sort-by first)))))

(defn render-toolbar [focus]
  (div
   {:style {:border-bottom (str "1px solid " (hsl 0 0 80)), :padding "4px 8px"}}
   (render-action "Append" :element/append)
   (render-action "Prepend" :element/prepend)
   (render-action "After" :element/after)
   (render-action "Before" :element/before)
   (render-action "Remove" :element/remove)))

(defcomp
 comp-layer-tree
 (tree focus)
 (div
  {:style (merge ui/flex ui/column {:background-color (hsl 0 0 100)})}
  (render-toolbar focus)
  (=< nil 8)
  (div
   {:style (merge ui/flex {:overflow :auto, :padding "4px 8px"})}
   (render-element tree focus []))))
