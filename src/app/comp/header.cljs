
(ns app.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp <> span div]]
            [respo.comp.space :refer [=<]]
            [app.style :as style]
            [feather.core :refer [comp-i]]))

(defn on-home [e dispatch!]
  (dispatch! :router/change {:name :home, :data nil, :router nil}))

(defn on-profile [e dispatch!]
  (dispatch! :router/change {:name :profile, :data nil, :router nil}))

(defcomp
 comp-header
 (logged-in? count)
 (div
  {:style (merge
           ui/column-parted
           {:font-size 24,
            :padding "8px 4px",
            :border-right (str "1px solid " (hsl 0 0 86))})}
  (div
   {:style ui/column-parted}
   (div
    {:on-click on-home, :style (merge style/icon {:cursor :pointer})}
    (comp-i :settings 14 (hsl 200 80 70)))
   (=< nil 8)
   (div
    {:style (merge style/icon),
     :on-click (fn [e d!] (d! :router/change {:name :code, :data nil, :router nil}))}
    (comp-i :code 14 (hsl 200 80 70)))
   (=< nil 8)
   (div
    {:style (merge style/icon {:color (hsl 240 100 76)}),
     :on-click (fn [e d!] (.open js/window (str js/location.href "?page=preview")))}
    (comp-i :eye 14 (hsl 200 80 70))))
  (div
   {:style ui/column}
   (div {:style (merge ui/center {:font-size 16, :font-family ui/font-fancy})} (<> count))
   (div
    {:style (merge style/icon), :on-click on-profile}
    (comp-i :message-square 14 (hsl 200 80 70))))))
