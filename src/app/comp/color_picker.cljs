
(ns app.comp.color-picker
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> list-> <> div button textarea span]]))

(def default-colors
  {:theme ["red" "green" "blue" "black" "white"],
   :font ["#444" "#888" "#ccc" "#fff"],
   :bg ["#ddd" "#aaf" "#88f"],
   :border ["bbb"]})

(defcomp
 comp-color-picker
 (states color on-pick)
 (let [cursor (:cursor states), state (or (:data states) {:category :theme, :popup? false})]
   (div
    {:style {:position :relative, :display :inline-block, :cursor :pointer}}
    (div
     {:inner-text (or color "none"),
      :style {:background-color (or color :white),
              :border "1px solid #aaa",
              :padding "0 8px",
              :font-family "Menlo,monospace",
              :color :white,
              :font-size 12},
      :on-click (fn [e d!] (d! cursor (update state :popup? not)))})
    (if (:popup? state)
      (div
       {:style {:position :absolute,
                :top "100%",
                :right 0,
                :background-color :white,
                :border (str "1px solid " (hsl 0 0 80))}}
       (list->
        {:style (merge ui/row)}
        (->> (keys default-colors)
             (map
              (fn [category]
                [category
                 (div
                  {:style (merge
                           {:min-width 60,
                            :text-align :center,
                            :cursor :pointer,
                            :border-bottom "1px solid #eee",
                            :border-color "#eee"}
                           (if (= category (:category state))
                             {:border-color (hsl 200 80 70)})),
                   :on-click (fn [e d!] (d! cursor (assoc state :category category)))}
                  (<> (name category)))]))))
       (list->
        {}
        (->> (get default-colors (:category state))
             (map
              (fn [item]
                [item
                 (div
                  {:style {:width 24,
                           :height 24,
                           :background-color item,
                           :margin 8,
                           :display :inline-block,
                           :cursor :pointer},
                   :on-click (fn [e d!] (println "pick" item) (on-pick item d!))})])))))))))
