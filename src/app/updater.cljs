
(ns app.updater
  (:require [app.updater.session :as session]
            [app.updater.user :as user]
            [app.updater.router :as router]
            [app.updater.element :as element]))

(defn updater [db op op-data sid op-id op-time]
  (let [method (case op
                 :session/connect session/connect
                 :session/disconnect session/disconnect
                 :session/focus session/focus
                 :user/log-in user/log-in
                 :user/sign-up user/sign-up
                 :user/log-out user/log-out
                 :session/remove-message session/remove-message
                 :router/change router/change
                 :element/append element/append-item
                 :element/prepend element/prepend-item
                 :element/after element/after-item
                 :element/before element/before-item
                 :element/remove element/remove-item
                 :element/set-kind element/set-kind
                 :element/layout element/set-layout
                 :element/content element/set-content
                 :element/styles element/set-styles
                 :element/presets element/set-presets
                 :element/change-style element/change-style
                 (identity nil))]
    (if (fn? method) (method db op-data sid op-id op-time) (do (println "Unknown op:" op) db))))
