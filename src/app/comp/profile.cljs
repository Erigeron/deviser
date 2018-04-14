
(ns app.comp.profile
  (:require [hsl.core :refer [hsl]]
            [app.schema :as schema]
            [respo-ui.core :as ui]
            [respo-ui.colors :as colors]
            [respo.macros :refer [defcomp <> span div a]]
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
            :background-color colors/motif-light,
            :color :white,
            :padding "0 8px"},
    :on-click (fn [e d! m!]
      (d! :user/log-out nil)
      (.removeItem js/localStorage (:local-storage-key schema/configs)))}
   (<> "Log out"))))
