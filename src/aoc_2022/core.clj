(ns aoc-2022.core
  (:require [clojure.string :as string]))

(defn read-input [n]
  (string/split-lines (slurp (str "resources/inputs/day" n ".txt"))))
