
(ns app.updater.element
  (:require [bisection-key.util :refer [key-append key-prepend key-after key-before]]
            [app.schema :as schema]
            [app.util :refer [wrap-path]]))

(defn change-style [store [k v]]
  (assoc-in store (concat (list :tree) (wrap-path (:focus store)) (list :styles k)) v))

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

(defn set-styles [store op-data]
  (assoc-in store (flatten (list :tree (wrap-path (:focus store)) :styles)) op-data))

(defn append-item [store op-data]
  (let [tree (:tree store)
        focus (:focus store)
        target-node (get-in tree (wrap-path focus))
        new-id (key-append (:children target-node))
        new-element schema/element]
    (if (= :box (:kind target-node))
      (-> store
          (update-in
           (cons :tree (wrap-path focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj focus new-id))))
      store)))

(defn prepend-item [store op-data]
  (let [tree (:tree store)
        focus (:focus store)
        target-node (get-in tree (wrap-path focus))
        new-id (key-prepend (:children target-node))
        new-element schema/element]
    (if (= :box (:kind target-node))
      (-> store
          (update-in
           (cons :tree (wrap-path focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj focus new-id))))
      store)))

(defn set-content [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:content)) op-data))

(defn set-presets [store op-data]
  (assoc-in store (flatten (list :tree (wrap-path (:focus store)) :presets)) op-data))

(defn set-kind [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:kind)) op-data))

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

(defn set-layout [store op-data]
  (assoc-in store (concat '(:tree) (wrap-path (:focus store)) '(:layout)) op-data))

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
