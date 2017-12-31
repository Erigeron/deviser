
(ns app.updater.element
  (:require [bisection-key.util :refer [key-append key-prepend key-after key-before]]
            [app.schema :as schema]
            [app.util :refer [wrap-path]]))

(defn append-item [store op-data]
  (let [tree (:tree store)
        focus (:focus store)
        target-node (get-in tree (wrap-path focus))
        new-id (key-append (:children target-node))
        new-element schema/element]
    (-> store
        (update-in
         (cons :tree (wrap-path focus))
         (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
        (update :focus (fn [focus] (conj focus new-id))))))

(defn prepend-item [store op-data]
  (let [tree (:tree store)
        focus (:focus store)
        target-node (get-in tree (wrap-path focus))
        new-id (key-prepend (:children target-node))
        new-element schema/element]
    (-> store
        (update-in
         (cons :tree (wrap-path focus))
         (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
        (update :focus (fn [focus] (conj focus new-id))))))

(defn after-item [store op-data]
  (if (empty? (:focus store))
    store
    (let [tree (:tree store)
          focus (:focus store)
          base-focus (pop focus)
          target-node (get-in tree (wrap-path base-focus))
          new-id (key-after (:children target-node) (last focus))
          new-element schema/element]
      (-> store
          (update-in
           (cons :tree (wrap-path base-focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj base-focus new-id)))))))

(defn remove-item [store op-data]
  (let [focus (:focus store), tree (:tree store)]
    (if (empty? focus)
      store
      (let [new-focus (pop focus)]
        (-> store
            (update-in
             (cons :tree (wrap-path new-focus))
             (fn [target-node]
               (update target-node :children (fn [children] (dissoc children (peek focus))))))
            (assoc :focus new-focus))))))

(defn before-item [store op-data]
  (if (empty? (:focus store))
    store
    (let [tree (:tree store)
          focus (:focus store)
          base-focus (pop focus)
          target-node (get-in tree (wrap-path base-focus))
          new-id (key-before (:children target-node) (last focus))
          new-element schema/element]
      (-> store
          (update-in
           (cons :tree (wrap-path base-focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj base-focus new-id)))))))

(defn set-kind [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:kind)) op-data))

(defn set-layout [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:layout)) op-data))

(defn set-content [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:content)) op-data))
