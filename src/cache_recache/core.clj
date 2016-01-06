(ns cache-recache.core
  (:require [clojure.core.async :refer :all]
            [chime :refer [chime-ch]]
            [clj-time.core :as t]))

(def ^:private cache-store (atom {}))

(defrecord Cache [data channel])
(defrecord CacheStrategy [name populate on-invalidate freq-mins])

(defn cache-recache
  "Sets up a cache and polling to re-cache"
  [strategy]
  (let [data ((:populate strategy))
        ch (chan)
        cache (Cache. data ch)
        chimes (chime-ch times {:ch (a/chan (a/sliding-buffer 1))})]
    (swap! cache-store assoc (:name strategy) cache)
    (go-loop []
      (when-let [time (<! chimes)]
        ;; ...
        (recur)))
    cache))
