
(ns app.comp.layer-tree
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.macros :refer [defcomp cursor-> list-> <> div button textarea span]]
            [verbosely.core :refer [verbosely!]]
            [app.style :as style]))

(defn render-element [tree focus path]
  (div
   {:style (merge (if (= focus path) {:border-left (str "1px solid " (hsl 60 90 30))}))}
   (let [kind (:kind tree)]
     (div
      {:style (merge
               style/button
               {:background-color (case kind :box (hsl 240 80 90) (hsl 0 0 50)),
                :padding "0 8px",
                :margin "1px 2px"}),
       :on-click (fn [e d! m!] (d! :focus path))}
      (<> kind)))
   (list->
    :div
    {:style {:padding "0 0 0 24px", :border-left (str "1px solid " (hsl 0 0 90))}}
    (->> (:children tree)
         (map (fn [[k v]] [k (render-element v focus (conj path k))]))
         (sort-by first)))))

(defn render-toolbar [focus]
  (div
   {}
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
  (div {:style (merge ui/flex {:overflow :auto})} (render-element tree focus []))))
