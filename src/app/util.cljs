
(ns app.util )

(defn log-js! [& args]
  (apply js/console.log (map (fn [x] (if (coll? x) (clj->js x) x)) args)))

(defn wrap-path [focus] (interleave (repeat :children) focus))
