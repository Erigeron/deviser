
(ns app.updater.session (:require [app.schema :as schema]))

(defn connect [db op-data session-id op-id op-time]
  (assoc-in db [:sessions session-id] (merge schema/session {:id session-id})))

(defn disconnect [db op-data session-id op-id op-time]
  (update db :sessions (fn [session] (dissoc session session-id))))

(defn focus [db op-data sid op-id op-time] (assoc-in db [:sessions sid :focus] op-data))

(defn remove-message [db op-data session-id op-id op-time]
  (update-in
   db
   [:sessions session-id :messages]
   (fn [messages] (dissoc messages (:id op-data)))))
