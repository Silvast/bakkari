(ns bakkari.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [bakkari.layout :refer [error-page]]
            [bakkari.routes.home :refer [home-routes]]
            [bakkari.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [bakkari.env :refer [defaults]]
            [mount.core :as mount]
            [bakkari.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'service-routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
