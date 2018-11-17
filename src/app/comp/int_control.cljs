
(ns app.comp.int-control
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.core :refer [defcomp <> span div button]]
            [app.style :as style]))

(defcomp
 comp-int-control
 (x step on-set-int)
 (div
  {}
  (<> (if (nil? x) "nil" (str x)))
  (button
   {:style style/button,
    :inner-text "+",
    :on-click (fn [e d! m!] (on-set-int (if (number? x) (+ x step) step) d!))})
  (button
   {:style style/button,
    :inner-text "-",
    :on-click (fn [e d! m!] (on-set-int (if (number? x) (- x step) step) d!))})
  (button
   {:style style/button, :inner-text "clear", :on-click (fn [e d! m!] (on-set-int nil d!))})))
