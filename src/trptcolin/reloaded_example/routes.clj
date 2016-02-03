(ns trptcolin.reloaded-example.routes
  (:require [compojure.core :as compojure :refer [GET]]
            [taoensso.timbre :as timbre]))

(defn make-routes []
  (compojure/routes
    (GET "/example" request
         (timbre/info "Received request: " request)
         {:status 200
          :body "This is an example response"})))
