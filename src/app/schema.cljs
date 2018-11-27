
(ns app.schema )

(def element
  {:kind :box, :content nil, :layout :center, :presets #{}, :styles {}, :children {}})

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router (do router {:name :home, :data nil, :router nil}),
   :messages {},
   :focus []})

(def user {:name nil, :id nil, :nickname nil, :avatar nil, :password nil})

(def database
  {:sessions (do session {}),
   :users (do user {}),
   :tree (merge element {:kind :box, :presets #{:fullscreen}})})

(def notification {:id nil, :kind nil, :text nil})
