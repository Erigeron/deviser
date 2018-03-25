
(ns app.schema )

(def configs {:storage-key "workflow-storage", :port 11002})

(def dev? (do ^boolean js/goog.DEBUG))
