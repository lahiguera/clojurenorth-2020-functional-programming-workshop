(ns katas.bowling.roll)

(defn strike?
  [first-roll]
  (= first-roll 10))

(defn spare?
  [first-roll second-roll]
  (= (+ first-roll
        second-roll)
     10))
