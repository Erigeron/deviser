
(ns app.comp.layer-tree
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [respo.comp.space :refer [=<]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(defn render-box [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 240 80 90), :padding "0 8px", :margin "1px 2px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (:layout tree))))

(defn render-icon [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 50), :padding "0 8px", :margin "1px 2px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (str "icon"))))

(defn render-space [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 50), :padding "0 8px", :margin "1px 2px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> "space")))

(defn render-text [tree path]
  (div
   {:style (merge
            style/button
            {:background-color (hsl 0 0 50), :padding "0 8px", :margin "1px 2px"}),
    :on-click (fn [e d! m!] (d! :focus path))}
   (<> (str "text:" (:content tree)))))

(defn render-element [tree focus path]
  (div
   {:style (merge (if (= focus path) {:border-left (str "1px solid " (hsl 60 90 30))}))}
   (let [kind (:kind tree)] )
   (case (:kind tree)
     :box (render-box tree path)
     :text (render-text tree path)
     :icon (render-icon tree path)
     :space (render-space tree path)
     "Unknown")
   (list->
    :div
    {:style {:padding "0 0 0 24px", :border-left (str "1px solid " (hsl 0 0 90))}}
    (->> (:children tree)
         (map (fn [[k v]] [k (render-element v focus (conj path k))]))
         (sort-by first)))))

(defn render-toolbar [focus]
  (div
   {:style {:border-bottom (str "1px solid " (hsl 0 0 80))}}
   (button
    {:style style/button, :on-click (fn [e d! m!] (d! :element/append nil))}
    (<> "append"))
   (button
    {:style style/button, :on-click (fn [e d! m!] (d! :element/prepend nil))}
    (<> "prepend"))
   (button {:style style/button, :on-click (fn [e d! m!] (d! :element/after))} (<> "after"))
   (button
    {:style style/button, :on-click (fn [e d! m!] (d! :element/before))}
    (<> "before"))
   (button
    {:style style/button, :on-click (fn [e d! m!] (d! :element/remove))}
    (<> "remove"))
   (<> (pr-str focus))))

(defcomp
 comp-layer-tree
 (tree focus)
 (div
  {:style (merge ui/flex ui/column {:padding 8, :background-color (hsl 0 0 96)})}
  (render-toolbar focus)
  (=< nil 8)
  (div {:style (merge ui/flex {:overflow :auto})} (render-element tree focus []))))
