
(ns app.server
  (:require [app.config :as config]
            [app.service :refer [run-server! sync-clients!]]
            [app.updater :refer [updater]]
            [cljs.reader :refer [read-string]]
            [app.util :refer [log-js!]]
            [cumulo-reel.reel :refer [reel-reducer refresh-reel reel-schema]]
            [app.schema :as schema]
            [app.node-config :as node-config]
            ["fs" :as fs]
            ["path" :as path]
            ["shortid" :as shortid]
            ["child_process" :as cp]))

(def initial-db
  (let [filepath (:storage-path node-config/env)]
    (if (fs/existsSync filepath)
      (do (println "Found storage.") (read-string (fs/readFileSync filepath "utf8")))
      schema/database)))

(defonce *reel (atom (merge reel-schema {:base initial-db, :db initial-db})))

(defonce *reader-reel (atom @*reel))

(defn dispatch! [op op-data sid]
  (let [op-id (.generate shortid), op-time (.valueOf (js/Date.))]
    (println "Dispatch!" (str op) op-data sid)
    (try
     (let [new-reel (reel-reducer @*reel updater op op-data sid op-id op-time)]
       (reset! *reel new-reel))
     (catch js/Error error (.error js/console error)))))

(defn persist-db! []
  (let [file-content (pr-str (assoc (:db @*reel) :sessions {}))
        now (js/Date.)
        storage-path (:storage-path node-config/env)
        backup-path (path/join
                     js/__dirname
                     "backups"
                     (str (inc (.getMonth now)))
                     (str (.getDate now) "-storage.edn"))]
    (println "path" storage-path)
    (fs/writeFileSync storage-path file-content)
    (cp/execSync (str "mkdir -p " (path/dirname backup-path)))
    (fs/writeFileSync backup-path file-content)
    (println "Saved file in" storage-path "and saved backup in" backup-path)))

(defn on-exit! [code] (persist-db!) (println "Saving file on exit" code) (.exit js/process))

(defn proxy-dispatch! [& args] "Make dispatch hot relodable." (apply dispatch! args))

(defn render-loop! []
  (if (not (identical? @*reader-reel @*reel))
    (do (reset! *reader-reel @*reel) (sync-clients! @*reader-reel)))
  (js/setTimeout render-loop! 300))

(defn main! []
  (run-server! proxy-dispatch! (:port config/site))
  (render-loop!)
  (.on js/process "SIGINT" on-exit!)
  (js/setInterval #(persist-db!) (* 60 1000 10))
  (println "Server started."))

(defn reload! []
  (println "Code updated.")
  (reset! *reel (refresh-reel @*reel initial-db updater))
  (sync-clients! @*reader-reel))
