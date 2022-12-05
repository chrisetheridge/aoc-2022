(ns aoc-2022.day1
  (:require [aoc-2022.core :as a22.core]
            [clojure.string :as string]))

(def input
  (->> (a22.core/read-input 1)
       string/split-lines
       (map parse-long)))

(defn part-one [_]
  (->> input
       (partition-by nil?)
       (remove #(= '(nil) %))
       (map (partial reduce +))
       (apply max)))

(defn part-two [_]
  (->> input
       (partition-by nil?)
       (remove #(= '(nil) %))
       (map (partial reduce +))
       sort
       (take-last 3)
       (reduce +)))

(defn run [_]
  (println "Part 1:" (part-one nil))
  (println "Part 2:" (part-two nil)))
