
(ns app.style (:require [respo-ui.core :as ui] [hsl.core :refer [hsl]]))

(def area-heading
  {:color (hsl 120 20 40),
   :font-size 18,
   :font-family "Josefin Sans, Helvetica, sans-serif",
   :font-weight 300,
   :margin-top 16})

(def button (merge ui/button {:margin "4px 4px", :line-height "24px", :min-width 60}))

(def font-fancy "Josefin Sans, Helvetica, Arial, sans-serif")

(def input (merge ui/input))

(def mono "Source Code Pro,Menlo,monospace")

(def textarea (merge ui/textarea))
