
(ns app.schema )

(def element
  {:kind :box, :content nil, :layout :center, :presets #{}, :styles {}, :children {}})

(def store
  {:states {}, :tree (merge element {:kind :box, :presets #{:fullscreen}}), :focus []})
