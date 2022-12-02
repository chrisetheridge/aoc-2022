(ns aoc-2022.day1
  (:require [clojure.string :as string]))

(def input
  (->> (slurp "resources/inputs/day1.txt")
       string/split-lines
       (map parse-long)))

(defn part-one [_]
  (->> input
       (partition-by nil?)
       (remove (partial every? nil?))
       (map (partial reduce +))
       (apply max)))

(defn part-two [_]
  (->> input
       (partition-by nil?)
       (remove (partial every? nil?))
       (map (partial reduce +))
       sort
       (take-last 3)
       (reduce +)))
