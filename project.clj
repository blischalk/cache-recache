(defproject cache-recache "0.1.0-SNAPSHOT"
  :description "Cache then re-cache data"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.11.0"]
                 [jarohen/chime "0.1.7"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]]
  :profiles {:dev {:dependencies [[honeysql "0.6.2"]
                                  [org.clojure/java.jdbc "0.4.2"]
                                  [speclj "3.3.1"]]}})
