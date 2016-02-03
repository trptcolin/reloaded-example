(ns trptcolin.reloaded-example.repl
  (:require
    ;; tools.namespace first to allow refresh on errors
    [clojure.tools.namespace.repl :refer [refresh clear set-refresh-dirs]]
    [speclj.run.standard]
    [trptcolin.reloaded-example.main :as main]
    [clojure.repl :refer [doc find-doc source pst]]
    [com.stuartsierra.component :as component]
    [taoensso.timbre :as timbre]))

(def system nil)

(defn start []
  (let [started-system (main/start-system main/application-options)]
    (alter-var-root #'system (constantly started-system))))

(defn stop []
  (component/stop-system system)
  (alter-var-root #'system (constantly nil)))

(defn reset []
  (stop)
  (set-refresh-dirs "src" "dev")
  (refresh :after 'trptcolin.reloaded-example.repl/start))

