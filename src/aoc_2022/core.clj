(ns aoc-2022.core)

(defn read-input [n]
  (slurp (str "resources/inputs/day" n ".txt")))

(defmacro timed [& body]
  `(let [t0#  (.getTime (java.util.Date.))
         res# (do ~@body)
         dt#  (- (.getTime (java.util.Date.)) t0#)]
     (str res# ", " dt# "ms")))
