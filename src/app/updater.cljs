
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
    :element/set-kind (element/set-kind store op-data)
    :element/layout (element/set-layout store op-data)
    :element/content (element/set-content store op-data)
    :element/styles (element/set-styles store op-data)
    :element/presets (element/set-presets store op-data)
    :element/change-style (element/change-style store op-data)
    store))
