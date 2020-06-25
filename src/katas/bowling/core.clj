(ns katas.bowling.core
  (:require [katas.bowling.roll :as roll]))

(defrecord Frame [type
                  first-roll
                  second-roll])

(defn- rolls->frames
  [rolls]
  (loop [frames             []
         [first-roll
          secon-roll
          & remaining-rolls
          :as all-rolls]    rolls]
    (cond
      (roll/strike? first-roll)           (recur (conj frames (->Frame "X" first-roll 0))
                                                 (rest all-rolls))
      (roll/spare? first-roll secon-roll) (recur (conj frames (->Frame "/" first-roll secon-roll))
                                                 remaining-rolls)
      :else                               (recur (conj frames (->Frame nil first-roll secon-roll))
                                                 remaining-rolls))))

(defn- reduce-rolls-fn
  [pins-knocked-down-in-first-attempt
   pins-knocked-down-in-second-attempt]
  (let [total-pins-knocked (+ pins-knocked-down-in-first-attempt
                              pins-knocked-down-in-second-attempt)]
    (cond
      (strike? pins-knocked-down-in-first-attempt)
      (+ 10 (* 2 total-pins-knocked))

      (spare? pins-knocked-down-in-first-attempt pins-knocked-down-in-second-attempt)
      (+ 10 (* 2 pins-knocked-down-in-first-attempt) pins-knocked-down-in-second-attempt)

      :else
      (+ pins-knocked-down-in-first-attempt pins-knocked-down-in-second-attempt))))

(defn total-score
  [rolls]
  (reduce reduce-rolls-fn rolls))
