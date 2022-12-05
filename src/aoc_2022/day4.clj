(ns aoc-2022.day4
  (:require [aoc-2022.core :as a22]
            [clojure.string :as string]
            [clojure.set :as set]))

(defn parse-to-ranges [pair]
  (let [[start end] (string/split pair #"-")]
    (range (parse-long start)
           (inc (parse-long end)))))

(defn explode-pairs [s]
  (->> (string/split s #",")
       (map parse-to-ranges)
       (map set)))

(def input
  (->> (a22/read-input 4)
       string/split-lines
       (map explode-pairs)))

(defn pairs-cover? [p1 p2]
  (or (set/subset? p1 p2)
      (set/superset? p1 p2)))

(def sample
  (->> "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"
       string/split-lines
       (map explode-pairs)))

(defn part-one [_]
  (->> input
       (map (fn [[p1 p2]]
              (pairs-cover? p1 p2)))
       (remove false?)
       count))

(defn part-two [_]
  (->> input
       (keep (fn [[p1 p2]]
               (seq (set/intersection p1 p2))))
       count))

(defn run [_]
  (println "Part 1:" (part-one nil))
  (println "Part 2:" (part-two nil)))
