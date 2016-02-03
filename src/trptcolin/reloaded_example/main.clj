(ns trptcolin.reloaded-example.main
  (:require [com.stuartsierra.component :as component]
            [trptcolin.reloaded-example.logging :as logging]
            [trptcolin.reloaded-example.http-server :as http-server]
            [trptcolin.reloaded-example.routes :as routes]
            [trptcolin.reloaded-example.shutdown :as shutdown]
            [compojure.core :as compojure :refer [GET]]
            [taoensso.timbre :as timbre]))

(def application-options {:logger {:rotation {:path "log/dev.log"}
                                   :to-standard-out? false}
                          :http-server {:port 3000
                                        :join? false}})

(defn create-application [options]
  (component/system-map
    :logger (logging/map->Logger {:options (:logger options)})
    :http-server (component/using
                   (http-server/new-http-server (:http-server options)
                                                (routes/make-routes))
                   [:logger])))

(defn start-system [options]
  (-> options
      create-application
      shutdown/add-shutdown-hook-to-system
      component/start-system))

(defn -main [& args]
  (try (start-system application-options)
       (catch Throwable t
         (.printStackTrace t))))
