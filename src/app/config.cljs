
(ns app.config )

(def cdn?
  (cond
    (exists? js/window) false
    (exists? js/process) (= "true" js/process.env.cdn)
    :else false))

(def dev?
  (let [debug? (do ^boolean js/goog.DEBUG)]
    (if debug?
      (cond
        (exists? js/window) true
        (exists? js/process) (not= "true" js/process.env.release)
        :else true)
      false)))

(def site
  {:port 11002,
   :title "Deviser",
   :icon "http://cdn.tiye.me/logo/erigeron.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/deviser/",
   :cdn-folder "tiye.me:cdn/deviser",
   :upload-folder "tiye.me:repo/Erigeron/deviser/",
   :server-folder "tiye.me:servers/deviser",
   :theme "#eeeeff",
   :storage-key "deviser-storage",
   :storage-file "storage.edn"})
