
(ns app.comp.profile
  (:require [hsl.core :refer [hsl]]
            [app.config :as config]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp <> span div a]]
            [respo.comp.space :refer [=<]]))

(defcomp
 comp-profile
 (user)
 (div
  {:style ui/flex}
  (<> (str "Hello! " (:name user)))
  (=< 8 nil)
  (a
   {:style {:font-size 14,
            :cursor :pointer,
            :background-color (hsl 200 80 90),
            :color :white,
            :padding "0 8px"},
    :on-click (fn [e d!]
      (d! :user/log-out nil)
      (.removeItem js/localStorage (:storage-key config/site)))}
   (<> "Log out"))))
