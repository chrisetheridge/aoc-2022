(ns aoc-2022.day8
  (:require [aoc-2022.core :as a22.core]
            [clojure.string :as string]))

(def input
  (string/split-lines (a22.core/read-input 8)))

(def sample
  (->> "30373
25512
65332
33549
35390"
       string/split-lines))

(defn input->grid [input]
  (->> input
       (map vec)
       (mapv (partial mapv (comp parse-long str)))))

(defn grid-exterior-size [grid]
  (- (* 2 (+ (count grid) (count (first grid)))) 4))

;; from https://groups.google.com/g/clojure-dev/c/NaAuBz6SpkY
(defn take-until
  "Returns a lazy sequence of successive items from coll until
   (pred item) returns true, including that item. pred must be
   free of side-effects."
  [pred coll]
  (lazy-seq
   (when-let [s (seq coll)]
     (if (pred (first s))
       (cons (first s) nil)
       (cons (first s) (take-until pred (rest s)))))))

(defn tree-tops [grid col-idx row-idx]
  (reverse (map #(nth (nth grid %) col-idx) (range 0 row-idx))))

(defn tree-bottoms [grid col-idx row-idx]
  (map #(nth (nth grid %) col-idx) (range (inc row-idx) (count grid))))

(defn tree-rights [grid row col-idx row-idx]
  (map #(nth (nth grid row-idx) %) (range (inc col-idx) (count row))))

(defn tree-lefts [grid col-idx row-idx]
  (map #(nth (nth grid row-idx) %) (range 0 col-idx)))

(defn part-one [_]
  (let [grid           (input->grid input)
        exterior-count (grid-exterior-size grid)]
    (->> grid
         (map-indexed (fn [row-idx row]
                        ;; Drop exterior tiles
                        (when-not (or (= row-idx 0) (= row-idx (dec (count grid))))
                          (->> row
                               (map-indexed (fn [col-idx col]
                                              (when-not (or (= col-idx 0) (= col-idx (dec (count row))))
                                                (let [tops    (tree-tops grid col-idx row-idx)
                                                      bottoms (tree-bottoms grid col-idx row-idx)
                                                      rights  (tree-rights grid row col-idx row-idx)
                                                      lefts   (tree-lefts grid col-idx row-idx)
                                                      taller? (partial > col)]
                                                  [col
                                                   (every? taller? tops)
                                                   (every? taller? rights)
                                                   (every? taller? bottoms)
                                                   (every? taller? lefts)]))))
                               (filter (comp some? first))))))
         (filter (comp some? first))
         (map (fn [row]
                (->> row
                     (filter (comp (partial some true?) rest))
                     count)))
         (reduce +)
         (+ exterior-count))))

(defn part-two [_]
  (let [grid (input->grid input)]
    (->> grid
         (map-indexed (fn [row-idx row]
                        ;; Drop exterior tiles
                        (when-not (or (= row-idx 0) (= row-idx (dec (count grid))))
                          (->> row
                               (map-indexed (fn [col-idx col]
                                              (when-not (or (= col-idx 0) (= col-idx (dec (count row))))
                                                (let [tops         (tree-tops grid col-idx row-idx)
                                                      bottoms      (tree-bottoms grid col-idx row-idx)
                                                      rights       (tree-rights grid row col-idx row-idx)
                                                      lefts        (tree-lefts grid col-idx row-idx)
                                                      shorter-or=? #(or (= % col) (< col %))]
                                                  [col
                                                   (take-until shorter-or=? tops)
                                                   (take-until shorter-or=? rights)
                                                   (take-until shorter-or=? bottoms)
                                                   (take-until shorter-or=? lefts)]))))
                               (filter (comp some? first))))))
         (filter (comp some? first))
         (mapcat (fn [row]
                   (->> row
                        (map (fn [[_tree & vis]]
                               (apply * (map count (keep seq vis))))))))
         (apply max))))

(defn run [_]
  (println "Part one:" (a22.core/timed (part-one nil)))
  (println "Part two:" (a22.core/timed (part-two nil))))
