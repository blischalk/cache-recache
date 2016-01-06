(ns cache-recache.core-spec
  (:require [cache-recache.core :refer :all]
            [clojure.core.async :refer :all]
            [speclj.core :refer :all])
  (:import [cache_recache.core CacheStrategy]))


(def basic-test-data {:guitar "string" :piano "percussion"})

(def basic-strategy
  (CacheStrategy. "instruments"
                   (fn [] basic-test-data)
                   #(println "hello world.")
                   1))

(describe "cache-recache"
  (it "returns a copy of cached data"
    (-> basic-strategy
        cache-recache
        :data
        (should= basic-test-data)))

  (it "returns a channel that acts like a channel"
    (-> basic-strategy
        cache-recache
        :channel
        close!
        should-not-throw))

  (it "checks cache validity every n interval"
    (let [call-counter (atom 0)
          strategy (CacheStrategy. "foobar"
                                   (fn [] (swap! call-counter inc))
                                   (fn [] )
                                   1)]
      (Thread/sleep 5000)
      (should (> @call-counter 1))))

  (it "notifies on channel a cache refresh"))
