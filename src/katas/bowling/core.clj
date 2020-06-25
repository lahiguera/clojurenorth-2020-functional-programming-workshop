(ns katas.bowling.core
  (:require [katas.bowling.roll :as roll]))

(defrecord Frame [type
                  first-roll
                  second-roll
                  score])

(defn- rolls->frames
  [rolls]
  (loop [frames             []
         [first-roll
          secon-roll
          & remaining-rolls
          :as all-rolls]    rolls]
    (cond
      (roll/strike? first-roll)           (recur (conj frames (->Frame "X" first-roll 0 10))
                                                 (rest all-rolls))
      (roll/spare? first-roll secon-roll) (recur (conj frames (->Frame "/" first-roll secon-roll 10))
                                                 remaining-rolls)
      :else                               (recur (conj frames (->Frame nil first-roll secon-roll (+ first-roll secon-roll)))
                                                 remaining-rolls))))

(defn- reduce-frames-fn
  [first-frame second-frame]
  (case (:type first-frame)
    "X"
    (->Frame "ACC" (:type second-frame) (+ 10 (:first-roll second-frame) (:second-roll second-frame)))

    "/"
    (+ 10 (* (:first-roll second-frame) (:second-roll second-frame)))

    :else
    (+ (:first-roll first-frame) (:second-roll first-frame))))

(defn total-score
  [rolls]
  (reduce reduce-frames-fn rolls))
