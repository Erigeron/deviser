
(ns app.updater.element
  (:require [bisection-key.util :refer [key-append key-prepend key-after key-before]]
            [app.schema :as schema]
            [app.util :refer [wrap-path]]))

(defn after-item [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (if (empty? focus)
      db
      (let [tree (:tree db)
            base-focus (pop focus)
            target-node (get-in tree (wrap-path base-focus))
            new-id (key-after (:children target-node) (last focus))
            new-element schema/element]
        (-> db
            (update-in
             (cons :tree (wrap-path base-focus))
             (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
            (update :focus (fn [focus] (conj base-focus new-id))))))))

(defn append-item [db op-data sid op-id op-time]
  (let [tree (:tree db)
        focus (get-in db [:sessions sid :focus])
        target-node (get-in tree (wrap-path focus))
        new-id (key-append (:children target-node))
        new-element schema/element]
    (if (= :box (:kind target-node))
      (-> db
          (update-in
           (cons :tree (wrap-path focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj focus new-id))))
      db)))

(defn before-item [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (if (empty? focus)
      db
      (let [tree (:tree db)
            base-focus (pop focus)
            target-node (get-in tree (wrap-path base-focus))
            new-id (key-before (:children target-node) (last focus))
            new-element schema/element]
        (-> db
            (update-in
             (cons :tree (wrap-path base-focus))
             (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
            (update :focus (fn [focus] (conj base-focus new-id))))))))

(defn change-style [db [k v] sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (concat (list :tree) (wrap-path focus) (list :styles k)) v)))

(defn prepend-item [db op-data sid op-id op-time]
  (let [tree (:tree db)
        focus (get-in db [:sessions sid :focus])
        target-node (get-in tree (wrap-path focus))
        new-id (key-prepend (:children target-node))
        new-element schema/element]
    (if (= :box (:kind target-node))
      (-> db
          (update-in
           (cons :tree (wrap-path focus))
           (fn [target-node] (assoc-in target-node [:children new-id] new-element)))
          (update :focus (fn [focus] (conj focus new-id))))
      db)))

(defn remove-item [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus]), tree (:tree db)]
    (if (empty? focus)
      db
      (let [new-focus (pop focus)]
        (-> db
            (update-in
             (cons :tree (wrap-path new-focus))
             (fn [target-node]
               (update target-node :children (fn [children] (dissoc children (peek focus))))))
            (assoc-in [:sessions sid :focus] new-focus))))))

(defn set-content [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (concat '(:tree) (wrap-path focus) '(:content)) op-data)))

(defn set-kind [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (concat '(:tree) (wrap-path focus) '(:kind)) op-data)))

(defn set-layout [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (concat '(:tree) (wrap-path focus) '(:layout)) op-data)))

(defn set-presets [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (flatten (list :tree (wrap-path focus) :presets)) op-data)))

(defn set-styles [db op-data sid op-id op-time]
  (let [focus (get-in db [:sessions sid :focus])]
    (assoc-in db (flatten (list :tree (wrap-path focus) :styles)) op-data)))
