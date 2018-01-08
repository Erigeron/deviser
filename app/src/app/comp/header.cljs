
(ns app.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.macros :refer [defcomp <> span div]]
            [respo.comp.space :refer [=<]]))

(defn on-home [e dispatch!]
  (dispatch! :router/change {:name :home, :data nil, :router nil}))

(defn on-profile [e dispatch!]
  (dispatch! :router/change {:name :profile, :data nil, :router nil}))

(def style-header
  {:height 48,
   :background-color colors/motif,
   :justify-content :space-between,
   :padding "0 16px",
   :font-size 16,
   :color :white})

(def style-logo {:cursor :pointer})

(def style-pointer {:cursor "pointer"})

(defcomp
 comp-header
 (logged-in?)
 (div
  {:style (merge ui/row-center style-header)}
  (div
   {:style ui/row}
   (div {:on-click on-home, :style style-logo} (<> "Deviser"))
   (=< 16 nil)
   (div
    {:style style-logo,
     :on-click (fn [e d! m!] (d! :router/change {:name :preview, :data nil, :router nil}))}
    (<> "Preview"))
   (=< 16 nil)
   (div
    {:style style-logo,
     :on-click (fn [e d! m!] (d! :router/change {:name :code, :data nil, :router nil}))}
    (<> "Code")))
  (div
   {:style style-pointer, :on-click on-profile}
   (<> span (if logged-in? "Me" "Guest") nil))))
