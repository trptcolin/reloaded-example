(ns trptcolin.reloaded-example.shutdown
  (:require [taoensso.timbre :as log]
            [com.stuartsierra.component :as component]))

(defn shutdown-hook [system]
  (Thread. #(do
              (log/info (str "Shutting down system with shutdown hook. system keys: "
                             (pr-str (keys system))))
              (try (component/stop-system system)
                   (catch Throwable t
                     (.printStackTrace t)))
              (log/info "Done shutting down system with shutdown hook"))))

(defrecord ShutdownHook [system]
  component/Lifecycle
  (start [component]
    (let [hook (shutdown-hook system)]
      (log/info "Adding shutdown hook")
      (.addShutdownHook (Runtime/getRuntime) hook)
      (assoc component :hook hook)))
  (stop [component]
    (when-let [hook (:hook component)]
      (let [removed (.removeShutdownHook (Runtime/getRuntime) hook)]
        (log/info (str "Found existing shutdown hook, removing it: " removed))))
    (dissoc component :hook)))

(defn add-shutdown-hook-to-system [system]
  (assoc system
         :shutdown-hook
         (component/using
           (map->ShutdownHook {:system system})
           (vec (keys system)))))
