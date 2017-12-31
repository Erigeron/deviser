
(ns app.schema )

(def element {:kind nil, :children {}, :layouts [], :styles {}, :content nil})

(def store
  {:states {}, :tree (merge element {:kind :layout, :layouts [:fullscreen]}), :focus []})
