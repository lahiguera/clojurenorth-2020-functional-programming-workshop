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
          second-roll
          & remaining-rolls
          :as all-rolls]    rolls]
    (cond
      (roll/strike? first-roll)           (recur (conj frames (->Frame "X" first-roll 0 10))
                                                 (rest all-rolls))
      (roll/spare? first-roll second-roll) (recur (conj frames (->Frame "/" first-roll second-roll 10))
                                                 remaining-rolls)
      :else                               (recur (conj frames (->Frame nil first-roll second-roll (+ first-roll second-roll)))
                                                 remaining-rolls))))

(defn- reduce-frames-fn
  [first-frame second-frame]
  (case (if (= "acc" (:type first-frame))
          (:type second-frame)
          (:type first-frame))
    "X"
    (->Frame "acc"
             (:first-roll second-frame)
             (:second-roll second-frame)
             (+ (:score first-frame) 10 (:first-roll second-frame) (:second-roll second-frame)))

    "/"
    (->Frame "acc"
             (:first-roll second-frame) (:second-roll second-frame)
             (+ (:score first-frame)
                10
                (* 2 (:first-roll second-frame))
                (:second-roll second-frame)))


    :else
    (->Frame "acc"
             (:first-roll second-frame)
             (:second-roll second-frame)
             (+ (:score first-frame)
                (:first-roll second-frame)
                (:second-roll second-frame)))))

(defn total-score
  [rolls]
  (let [acc (->Frame "acc" 0 0 0)]
    (reduce reduce-frames-fn acc (map rolls->frames rolls))))
