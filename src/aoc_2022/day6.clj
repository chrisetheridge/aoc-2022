(ns aoc-2022.day6
  (:require [aoc-2022.core :as a22.core]))

(def input
  (a22.core/read-input 6))

;; sample + expected result
(def samples
  [["bvwbjplbgvbhsrlpgdmjqwftvncz" 5]
   ["nppdvjthqldpwncqszvftbrmjlhg" 6]
   ["nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 10]
   ["zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" 11]])

(defn find-marker-position [n chars]
  (loop [pos 0
         chars chars]
    (let [probe (take n chars)]
      (if (apply distinct? probe)
        [probe (+ n pos)]
        (recur (inc pos) (next chars))))))

(defn part-one [_]
  (last (find-marker-position 4 input)))

(defn part-two [_]
  (last (find-marker-position 14 input)))

(defn run [_]
  (println "Part one:" (a22.core/timed (part-one nil)))
  (println "Part two:" (a22.core/timed (part-two nil))))
