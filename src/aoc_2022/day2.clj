(ns aoc-2022.day2
  (:require [aoc-2022.core :as a22]
            [clojure.string :as string]
            [clojure.set :as set]))

(def translate
  {"A" :rock
   "B" :paper
   "C" :scissors
   "X" :rock
   "Y" :paper
   "Z" :scissors})

(def beats
  {:paper    :rock
   :scissors :paper
   :rock     :scissors})

(def loses
  (set/map-invert beats))

(def shape-scores
  {:rock     1
   :paper    2
   :scissors 3})

(def result-scores
  {:win  6
   :draw 3
   :lose 0})

(def translate-desired-result
  {"X" :lose
   "Y" :draw
   "Z" :win})

(def input
  (->> (a22/read-input 2)
       string/split-lines
       (map #(string/split % #" "))))

(defn determine-round [[oppo me]]
  (let [me-shape      (translate me)
        oppo-shape    (translate oppo)
        beating-shape (get beats me-shape)]
    {:me-shape me-shape
     :result   (cond (= beating-shape oppo-shape) :win
                     (= oppo-shape me-shape)      :draw
                     :else                        :lose)}))

(defn round-score [me-shape round-result]
  (+ (get result-scores round-result)
     (get shape-scores me-shape)))

(defn part-one [_]
  (->> (for [round input
             :let  [{:keys [me-shape result]} (determine-round round)]]
         (round-score me-shape result))
       (reduce +)))

(defn desired-pick [desired-result oppo-shape]
  (case desired-result
    :draw oppo-shape
    :win  (get loses oppo-shape)
    :lose (get beats oppo-shape)))

(defn part-two [_]
  (->> (for [[oppo desired-result] input
             :let                  [oppo-shape (translate oppo)
                                    desired (translate-desired-result desired-result)
                                    my-pick (desired-pick desired oppo-shape)]]
         (round-score my-pick desired))
       (reduce +)))

(defn run [_]
  (println "Part 1:" (part-one nil))
  (println "Part 2:" (part-two nil)))
