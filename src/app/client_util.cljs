
(ns app.client-util )

(defn wrap-path [focus] (interleave (repeat :children) focus))
