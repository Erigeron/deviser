
(ns app.twig.container
  (:require [recollect.twig :refer [deftwig]] [app.twig.user :refer [twig-user]]))

(deftwig
 twig-container
 (db session records)
 (let [logged-in? (some? (:user-id session))
       router (:router session)
       base-data {:logged-in? logged-in?,
                  :session session,
                  :count (:count db),
                  :reel-length (count records)}]
   (merge
    base-data
    (if logged-in?
      {:user (twig-user (get-in db [:users (:user-id session)])),
       :router router,
       :tree (db :tree),
       :focus (:focus session),
       :count (count (:sessions db))}
      nil))))
