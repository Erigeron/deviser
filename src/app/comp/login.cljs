
(ns app.comp.login
  (:require [respo.core :refer [defcomp <> div input button span]]
            [respo.comp.space :refer [=<]]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo-ui.core :as ui]
            [app.config :as config]))

(def initial-state {:username "", :password ""})

(defn on-input [state cursor k] (fn [e d!] (d! cursor (assoc state k (:value e)))))

(defn on-submit [username password signup?]
  (fn [e dispatch!]
    (dispatch! (if signup? :user/sign-up :user/log-in) [username password])
    (.setItem js/localStorage (:storage-key config/site) [username password])))

(defcomp
 comp-login
 (states)
 (let [cursor (:cursor states), state (or (:data states) initial-state)]
   (div
    {}
    (div
     {:style {}}
     (div
      {}
      (input
       {:placeholder "Username",
        :value (:username state),
        :style ui/input,
        :on-input (on-input state cursor :username)})))
    (=< nil 8)
    (div
     {:style ui/flex}
     (button
      {:inner-text "Sign up",
       :style (merge ui/button {:outline :none, :border :none}),
       :on-click (on-submit (:username state) (:password state) true)})
     (=< 8 nil)
     (button
      {:inner-text "Sign in",
       :style (merge ui/button {:outline :none, :border :none}),
       :on-click (on-submit (:username state) (:password state) false)})))))
