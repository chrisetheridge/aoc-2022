(ns init
  (:require [babashka.fs :as fs]
            [babashka.curl :as curl]))

(defn source-str [d]
  (str "(ns aoc-2022.day" d "
  (:require [aoc-2022.core :as a22.core]))

(def input
  (a22.core/read-input " d "))"
       "

(defn part-one [_])

(defn part-two [_])

(defn run [_]
  (println \"Part one:\" (a22.core/timed (part-one nil)))
  (println \"Part two:\" (a22.core/timed (part-two nil))))"))

(defn session-key []
  (if-some [key (System/getenv "AOC_SESSION")]
    key
    (do
      (println "Could not get session key from environment")
      (println "Get your session key from adventofcode.com and set AOC_SESSION to its value."))))

(defn fetch-input [n]
  (when-some [key (session-key)]
    (let [url (str "https://adventofcode.com/2022/day/" n "/input")]
      (try
        (-> (curl/get url {:headers {"Cookie" (str "session=" key)}})
            :body)
        (catch Exception ex
          (println "Could not fetch input from" url
                   (.getMessage ex))
          (println "The input file will be created but is empty.")
          (.printStackTrace ex))))))

(defn init-day
  {:org.babashka/cli {:coerce {:day :int}
                      :alias  {:d :day}}}
  [{:keys [day]}]
  (println "Creating source and input for new day" day)
  (let [day-name (str "day" day)
        clj-path (str "src/aoc_2022/" day-name ".clj")
        input-path (str "resources/inputs/" day-name ".txt")]
    (if (fs/exists? clj-path)
      (println (str day-name ".clj") "already exists, skipping.")
      (do
        (println "Creating source for" day)
        (fs/create-file clj-path)
        (spit clj-path (source-str day))))
    (if (fs/exists? input-path)
      (println (str day-name ".txt") "already exists, skipping.")
      (do
        (println "Creating input for day" day)
        (fs/create-file input-path)
        (spit input-path (fetch-input day))))))
