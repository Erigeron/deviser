
(ns server.updater.core
  (:require [server.updater.session :as session]
            [server.updater.user :as user]
            [server.updater.router :as router]
            [server.updater.element :as element]))

(defn updater [db op op-data session-id op-id op-time]
  (case op
    :session/connect (session/connect db op-data session-id op-id op-time)
    :session/disconnect (session/disconnect db op-data session-id op-id op-time)
    :user/log-in (user/log-in db op-data session-id op-id op-time)
    :user/sign-up (user/sign-up db op-data session-id op-id op-time)
    :user/log-out (user/log-out db op-data session-id op-id op-time)
    :session/remove-notification
      (session/remove-notification db op-data session-id op-id op-time)
    :router/change (router/change db op-data session-id op-id op-time)
    :focus (assoc db :focus op-data)
    :element/append (element/append-item db op-data )
    :element/prepend (element/prepend-item db op-data)
    :element/after (element/after-item db op-data)
    :element/before (element/before-item db op-data)
    :element/remove (element/remove-item db op-data)
    :element/set-kind (element/set-kind db op-data)
    :element/layout (element/set-layout db op-data)
    :element/content (element/set-content db op-data)
    :element/styles (element/set-styles db op-data)
    :element/presets (element/set-presets db op-data)
    :element/change-style (element/change-style db op-data)
    db))
