(ns bakkari.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))
            [bakkari.db.core :as db]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []
    :tags ["bakkari"]

    (GET "/messages" []
      :return       [{:guest String :message String}]
      :summary      "get messages"
      (ok (db/get-messages)))

    (POST "/message" request
      :return      Long
      :body-params [guest :- String message :- String]
      :summary     "add new message"
      (ok (db/add-message {:guest guest
                           :message message})))))
