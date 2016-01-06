(ns cache-recache.core
  (:require [clojure.core.async :refer :all]
            [chime :refer [chime-ch]]
            [clj-time.core :as t]
            [clj-time.periodic :refer [periodic-seq]]))

(def ^:private cache-store (atom {}))

(defrecord Cache [data channel])
(defrecord CacheStrategy [name populate on-invalidate freq-mins])

(defn cache-it [name f ch]
  (let [data (f)]
    (swap! cache-store assoc name (Cache. data ch))))

(defn cache-recache
  "Sets up a cache and polling to re-cache"
  [strategy]
  (let [handler (:populate strategy)
        data (handler)
        name (:name strategy)
        ch (chan)
        cache (Cache. data ch)
        times (periodic-seq (t/now) (-> (:freq-mins strategy) t/seconds))
        chimes (chime-ch times {:ch (chan (sliding-buffer 1))})]
    ;; Prime the pump with initial dataset
    (swap! cache-store assoc name cache)

    ;; Schedule polling
    (go-loop []
      (when-let [time (<! chimes)]
        (cache-it name handler ch)
        (recur)))
    cache))
