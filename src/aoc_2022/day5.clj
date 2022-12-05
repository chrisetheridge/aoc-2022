(ns aoc-2022.day5
  (:require [aoc-2022.core :as a22]
            [clojure.string :as string]))

(def input
  (a22/read-input 5))

(def sample
  "    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(defn stack [xs]
  {(-> xs
       last
       str
       parse-long)
   (reverse (drop-last xs))})

(defn initial-state [s]
  (let [[start instr] (string/split s #"\n\n")]
    {:boxes (->> (string/split-lines start)
                 (apply map vector)
                 (keep (comp seq (partial remove #(#{\space \[ \]} %))))
                 (map stack)
                 (apply merge))
     :steps (->> instr
                 string/split-lines
                 (map (comp (partial map parse-long) #(re-seq #"\d+" %))))}))

;; [count from to]

(defn apply-steps [reverse? init-state]
  (reduce (fn [state step]
            (let [{:keys [boxes]} state
                  [n from to]     step
                  target          (cond-> (take-last n (get boxes from))
                                    reverse? reverse)]
              (-> state
                  (update-in [:boxes from] (fn [stack]
                                             (drop-last n stack)))
                  (update-in [:boxes to] (fn [stack]
                                           (concat stack target)))
                  (update :boxes (partial into (sorted-map))))))
          init-state
          (:steps init-state)))

(defn sort-boxes [{:keys [boxes]}]
  (->> boxes
       vals
       (map last)
       (apply str)))

(defn part-1 [_]
  (->> (initial-state input)
       (apply-steps true)
       sort-boxes))

(defn part-2 [_]
  (->> (initial-state input)
       (apply-steps false)
       sort-boxes))
