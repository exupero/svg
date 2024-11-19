(ns svg.core
  (:require [clojure.string :as string])
  #?(:import [goog.string format]))

(defn translate [x y]
  (format "translate(%f,%f)" x y))

(defn rotate [d]
  (format "rotate(%f)" d))

(defn scale
  ([x] (scale x x))
  ([x y] (format "scale(%f,%f)" x y)))

(defn transformations [ts]
  (string/join (reverse ts)))

(defn path [pts]
  (transduce
    (comp
      (map #(apply format "%f,%f" %))
      (interpose "L"))
    str "M" pts))

(defn closed-path [pts]
  (str (path pts) "Z"))
