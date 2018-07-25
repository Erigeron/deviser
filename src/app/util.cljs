
(ns app.util )

(defn find-first [f xs] (reduce (fn [_ x] (when (f x) (reduced x))) nil xs))

(defn get-env! [property] (aget (.-env js/process) property))

(defn log-js! [& args]
  (apply js/console.log (map (fn [x] (if (coll? x) (clj->js x) x)) args)))

(defn wrap-path [focus] (interleave (repeat :children) focus))
