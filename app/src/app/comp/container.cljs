
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.macros :refer [defcomp <> div span button]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.comp.space :refer [=<]]
            [app.comp.header :refer [comp-header]]
            [app.comp.profile :refer [comp-profile]]
            [app.comp.login :refer [comp-login]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]
            [app.comp.reel :refer [comp-reel]]
            [app.comp.home :refer [comp-home]]
            [app.comp.previewer :refer [comp-previewer]]
            [app.comp.code-reader :refer [comp-code-reader]]))

(def style-alert {:font-family "Josefin Sans", :font-weight 100, :font-size 40})

(def style-body {:padding "8px 16px"})

(def style-debugger {:bottom 0, :left 0, :max-width "100%"})

(defcomp
 comp-container
 (states store)
 (let [state (:data states), session (:session store)]
   (if (nil? store)
     (div
      {:style (merge ui/global ui/fullscreen ui/center)}
      (span
       {:style {:cursor :pointer}, :on-click (fn [e d! m!] (d! :effect/connect nil))}
       (<> "No connection!" style-alert)))
     (div
      {:style (merge ui/global ui/fullscreen ui/column)}
      (comp-header (:logged-in? store))
      (div
       {:style (merge ui/flex ui/column style-body)}
       (if (:logged-in? store)
         (let [router (:router store)]
           (case (:name router)
             :profile (comp-profile (:user store))
             :home (comp-home states store)
             :preview (comp-previewer (:tree store) (:focus store))
             :code (comp-code-reader (:tree store))
             (<> (str "404 " (:name router)))))
         (comp-login states)))
      (comp-inspect "Store" store style-debugger)
      (comp-msg-list (get-in store [:session :notifications]) :session/remove-notification)
      (comp-reel (:reel-length store) {})))))
