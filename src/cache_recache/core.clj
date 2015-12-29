(ns cache-recache.core)

(def ^:private cache (atom {}))

(defrecord CacheStrategy [name populate on-invalidate freq-mins])

(defn cache-recache
  "Sets up a cache and polling to re-cache"
  [strategy]
  (let [data ((:populate strategy))]
    (swap! cache assoc (:name strategy) data)
    [data :foo]))
