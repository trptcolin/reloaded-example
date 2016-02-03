(ns trptcolin.reloaded-example.http-server
  (:require [compojure.core :as compojure]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty9 :as jetty]))

(defrecord HTTPServer [config app server]
  component/Lifecycle
  (start [component]
    (let [server (jetty/run-jetty app config)]
      (assoc component :server server)))
  (stop [component]
    (when server (.stop server))
    (assoc component :server nil)))

(defn new-http-server [config app]
  (map->HTTPServer {:config config :app app}))
