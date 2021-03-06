
(ns app.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp <> >> div span button]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo.comp.space :refer [=<]]
            [app.comp.header :refer [comp-header]]
            [app.comp.profile :refer [comp-profile]]
            [app.comp.login :refer [comp-login]]
            [respo-message.comp.messages :refer [comp-messages]]
            [app.comp.home :refer [comp-home]]
            [app.comp.previewer :refer [comp-previewer]]
            [app.comp.code-reader :refer [comp-code-reader]]
            [app.config :refer [dev?]]
            [cumulo-reel.comp.reel :refer [comp-reel]]))

(defcomp
 comp-offline
 ()
 (span
  {:style {:cursor :pointer}, :on-click (fn [e d!] (d! :effect/connect nil))}
  (<>
   "Socket brokwn! Click to retry."
   {:font-family ui/font-fancy, :font-weight 100, :font-size 32})))

(def style-body {:padding "8px 16px"})

(defcomp
 comp-container
 (states store)
 (let [state (:data states), session (:session store), router (:router store)]
   (if (nil? store)
     (div {:style (merge ui/global ui/fullscreen ui/center)} (comp-offline))
     (if (= :preview (:name router))
       (comp-previewer (:tree store) (:focus store))
       (div
        {:style (merge ui/global ui/fullscreen ui/row)}
        (comp-header (:logged-in? store) (:count store))
        (div
         {:style (merge ui/flex ui/column style-body)}
         (if (:logged-in? store)
           (case (:name router)
             :profile (comp-profile (:user store))
             :home (comp-home states store)
             :code (comp-code-reader (:tree store))
             (<> (str "404 " (:name router))))
           (comp-login states)))
        (when dev? (comp-inspect "Store" store {:bottom 30, :right 0, :max-width "100%"}))
        (comp-messages
         (get-in store [:session :messages])
         {}
         (fn [info d!] (d! :session/remove-message info)))
        (when dev? (comp-reel (:reel-length store) {:bottom 0})))))))
