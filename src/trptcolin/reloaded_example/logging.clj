(ns trptcolin.reloaded-example.logging
  (:require [com.stuartsierra.component :as component]
            [taoensso.timbre.appenders.3rd-party.rotor :as rotor]
            [taoensso.timbre :as timbre]))

(defn use-logger! [{:keys [path to-standard-out? rotation] :as options}]
  (timbre/merge-config!
    {:appenders {:rotor (rotor/rotor-appender rotation)}})
  (timbre/merge-config!
    {:appenders {:println {:enabled? to-standard-out?}}}))

(defrecord Logger [options]
  component/Lifecycle
  (start [component]
    (use-logger! options)
    component)
  (stop [component] component))
