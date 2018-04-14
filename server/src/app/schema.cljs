
(ns app.schema )

(def configs
  {:storage-key "/data/Erigeron/deviser.edn",
   :local-storage-key "deviser-storage",
   :port 11002})

(def element
  {:kind :box, :content nil, :layout :center, :presets #{}, :styles {}, :children {}})

(def database
  {:sessions {},
   :users {},
   :topics {},
   :tree (merge element {:kind :box, :presets #{:fullscreen}})})

(def dev? (do ^boolean js/goog.DEBUG))

(def notification {:id nil, :kind nil, :text nil})

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router {:name :home, :data nil, :router nil},
   :notifications [],
   :focus []})

(def user {:name nil, :id nil, :nickname nil, :avatar nil, :password nil})
