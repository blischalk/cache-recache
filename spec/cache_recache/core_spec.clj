(ns cache-recache.core-spec
  (:require [speclj.core :refer :all]
            [cache-recache.core :refer :all])
  (:import [cache_recache.core CacheStrategy]))


(def basic-test-data {:guitar "string" :piano "percussion"})

(def basic-strategy
  (CacheStrategy. "instruments"
                   (fn [] basic-test-data)
                   #(println "hello world.")
                   60))

(describe "cache-recache"
  (it "returns a copy of cached data"
    (let [[cached ch] (cache-recache basic-strategy)]
      (should= cached basic-test-data))))
