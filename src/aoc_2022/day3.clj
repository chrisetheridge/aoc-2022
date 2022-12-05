(ns aoc-2022.day3
  (:require [aoc-2022.core :as a22]
            [clojure.set :as set]
            [clojure.string :as string]))

(def input
  (string/split-lines (a22/read-input 3)))

(def sample
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])

(defn split-parts [s]
  (split-at (/ (count s) 2) s))

(defn common-items [parts]
  (apply set/intersection (map set parts)))

;; 97 - 122
;; 65 - 90
#_[(int \a)
   (int \z)]
#_[(int \A)
   (int \Z)]

(defn priority [c]
  (let [n (int c)]
    (if (<= 65 n 90)
      (- n 38)
      (- n 96))))

(defn part-one [_]
  (->> input
       (map (comp priority first common-items split-parts))
       (reduce +)))

(def sample2
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])

(defn part-two [_]
  (->> input
       (partition-all 3)
       (map (comp priority first common-items vec))
       (reduce +)))

(defn run [_]
  (println "Part 1:" (part-one nil))
  (println "Part 2:" (part-two nil)))

(comment
  (priority \a)

  (>= 65 (int \A))

  ;; 97 - 122
  ;; 65 - 90

  [(int \a)
   (int \z)]

  [(int \A)
   (int \Z)]

  (- (int \z) 96)
  (- (int \A) 38))
