(defproject org.clojars.trptcolin/reloaded-example "0.0.1-SNAPSHOT"
  :description "An example app to demonstrate the reloaded workflow"
  :pedantic? :abort

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.2.2"]

                 ;; logging
                 [com.taoensso/timbre "4.2.1"]
                 [org.slf4j/log4j-over-slf4j "1.7.9"]
                 [org.slf4j/slf4j-nop "1.7.2"]

                 ;; web stuff
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0" :exclusions [org.clojure/tools.reader]]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring/ring-devel "1.4.0"]
                 ;; This one has more threadpool configuration options than ring/ring-jetty-adapter
                 ;; https://github.com/sunng87/ring-jetty9-adapter/commit/6f790ad95019a4b088572cf1b66fbb76a2f993c9
                 [info.sunng/ring-jetty9-adapter "0.9.2"]]

  :repl-options {:init-ns trptcolin.reloaded-example.repl}
  :main trptcolin.reloaded-example.main

  :profiles {:dev {:source-paths ["src" "dev"]
                   :repl-options {:init-ns trptcolin.reloaded-example.repl}
                   :dependencies [[org.clojure/tools.namespace "0.2.10"]
                                  [speclj "3.3.1"]]}}

  :jvm-opts ["-Xmx1G" "-Djava.awt.headless=true"
             "-XX:+HeapDumpOnOutOfMemoryError"
             "-Xloggc:log/gc.log"
             "-verbose:gc" "-XX:+PrintGCDateStamps" "-XX:+PrintGCTimeStamps"
             "-XX:+PrintGCDetails" "-XX:+UseGCLogFileRotation"
             "-XX:NumberOfGCLogFiles=5" "-XX:GCLogFileSize=16M"]

  :plugins [[speclj "3.3.1"]])
