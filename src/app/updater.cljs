
(ns app.updater (:require [respo.cursor :refer [mutate]] [app.updater.element :as element]))

(defn updater [store op op-data]
  (case op
    :states (update store :states (mutate op-data))
    :focus (assoc store :focus op-data)
    :element/append (element/append-item store op-data)
    :element/prepend (element/prepend-item store op-data)
    :element/after (element/after-item store op-data)
    :element/before (element/before-item store op-data)
    :element/remove (element/remove-item store op-data)
    store))
