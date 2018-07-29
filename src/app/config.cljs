
(ns app.config (:require [app.util :refer [get-env!]]))

(def bundle-builds #{"release" "local-bundle"})

(def dev?
  (if (exists? js/window)
    (do ^boolean js/goog.DEBUG)
    (not (contains? bundle-builds (get-env! "mode")))))

(def site
  {:storage-key "deviser-storage",
   :port 11002,
   :title "Deviser",
   :icon "http://cdn.tiye.me/logo/erigeron.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/deviser/",
   :cdn-folder "tiye.me:cdn/deviser",
   :upload-folder "tiye.me:repo/Erigeron/deviser/",
   :server-folder "tiye.me:servers/deviser"})