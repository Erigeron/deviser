
(ns app.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.core :refer [defcomp <> span div]]
            [respo.comp.space :refer [=<]]
            [respo-ui.comp.icon :refer [comp-icon]]
            [app.style :as style]))

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
    (comp-icon :settings))
   (=< nil 8)
   (div
    {:style (merge style/icon),
     :on-click (fn [e d! m!] (d! :router/change {:name :code, :data nil, :router nil}))}
    (comp-icon :quote))
   (=< nil 8)
   (div
    {:style (merge style/icon {:color (hsl 240 100 76)}),
     :on-click (fn [e d! m!] (.open js/window (str location.href "?page=preview")))}
    (comp-icon :ios-eye)))
  (div
   {:style ui/column}
   (div {:style (merge ui/center {:font-size 16, :font-family ui/font-fancy})} (<> count))
   (div {:style (merge style/icon), :on-click on-profile} (comp-icon :android-contact)))))
