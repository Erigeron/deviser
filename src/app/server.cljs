
(ns app.server
  (:require [app.schema :as schema]
            [app.service :refer [run-server! sync-clients!]]
            [app.updater :refer [updater]]
            [cljs.reader :refer [read-string]]
            [app.util :refer [try-verbosely! log-js!]]
            [app.reel :refer [reel-updater refresh-reel reel-schema]]
            [verbosely.core :refer [log!]]
            ["fs" :as fs]
            ["shortid" :as shortid]))

(def initial-db
  (let [filepath (:storage-key schema/configs)]
    (if (fs/existsSync filepath)
      (do (println "Found storage.") (read-string (fs/readFileSync filepath "utf8")))
      schema/database)))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defonce *reader-reel (atom @*reel))

(defn dispatch! [op op-data sid]
  (let [op-id (.generate shortid), op-time (.valueOf (js/Date.))]
    (log! "Dispatch!" (str op) op-data sid)
    (try-verbosely!
     (let [new-reel (reel-updater @*reel updater op op-data sid op-id op-time)]
       (reset! *reel new-reel)))))

(defn on-exit! [code]
  (fs/writeFileSync (:storage-key schema/configs) (pr-str (assoc (:db @*reel) :sessions {})))
  (println "Saving file on exit" code)
  (.exit js/process))

(defn proxy-dispatch! [& args] "Make dispatch hot relodable." (apply dispatch! args))

(defn render-loop! []
  (if (not (identical? @*reader-reel @*reel))
    (do (reset! *reader-reel @*reel) (sync-clients! @*reader-reel)))
  (js/setTimeout render-loop! 300))

(defn main! []
  (run-server! proxy-dispatch! (:port schema/configs))
  (render-loop!)
  (.on js/process "SIGINT" on-exit!)
  (println "Server started."))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-reel))