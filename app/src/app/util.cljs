
(ns app.util )

(defn wrap-path [focus] (interleave (repeat :children) focus))
