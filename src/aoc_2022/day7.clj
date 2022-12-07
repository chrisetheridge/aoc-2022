(ns aoc-2022.day7
  (:require [aoc-2022.core :as a22.core]
            [clojure.string :as string]))

(def input
  (string/split-lines (a22.core/read-input 7)))

(def sample
  (->> "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"
       string/split-lines))

(defn folder-contents [cmds]
  (->> cmds
       (take-while #(not= (first %) \$))
       (mapcat (partial re-seq #"\d+"))
       (map parse-long)))

(defn apply-commands [instructions]
  (loop [folders  {}
         path     []
         commands instructions]
    (if-not (seq commands)
      folders
      (let [[curr & next-cmds]  commands
            [marker cmd target] (string/split curr #"\s+" )]
        (cond
          (= curr "$ cd /")
          (recur folders ["/"] next-cmds)

          (= curr "$ ls")
          ;; each listing is
          ;; $ ls
          ;; dir 123
          ;; foo 123
          ;; bar 123
          ;; so we can just list all of x + n, and then drop those
          (recur (assoc folders path (folder-contents next-cmds))
                 path
                 (drop-while #(not= (first %) \$) next-cmds))

          (= curr "$ cd ..")
          (recur folders
                 (pop path)
                 next-cmds)

          (= [marker cmd] ["$" "cd"])
          (recur folders
                 (conj path target)
                 next-cmds))))))

(defn subfolder-of? [f1 [f2 _]]
  (= f1 (take (count f1) f2)))

(defn folder-size [folder paths]
  (->> (filter (partial subfolder-of? folder) paths)
       (mapcat second)
       (reduce +)))

(defn subfolder-sizes [fs]
  (let [folders (map first fs)]
    (->> folders
         (map (fn [folder]
                (folder-size folder fs))))))

(comment
  ;; should = 95437
  (->> sample
       apply-commands
       subfolder-sizes
       (filter #(<= % 100000))
       (reduce +)))

(defn part-one [_]
  (->> input
       apply-commands
       subfolder-sizes
       (filter #(<= % 100000))
       (reduce +)))

(def disk-space 70000000)

(def update-size 30000000)

(defn part-two [_]
  (let [paths     (apply-commands input)
        root-size (folder-size ["/"] paths)
        free-space (- disk-space root-size)
        required-space (- update-size free-space)]
    (->> (subfolder-sizes paths)
         (filter #(>= % required-space))
         (apply min))))

(defn run [_]
  (println "Part one:" (a22.core/timed (part-one nil)))
  (println "Part two:" (a22.core/timed (part-two nil))))
