(ns svg.core
  (:require [clojure.string :as str]))

(defn view-box [x y w h]
  (str x " " y " " w " " h))

(defn translate [x y]
  (str "translate(" x "," y ")"))

(defn rotate [d]
  (str "rotate(" d ")"))

(defn scale
  ([s]
   (str "scale(" s ")"))
  ([x y]
   (str "scale(" x ", " y ")")))

(defn transformations [ts]
  (str/join (reverse ts)))

(defn path [pts]
  (transduce
    (comp
      (map (fn [[x y]]
             (str x \, y)))
      (interpose "L"))
    str "M" pts))

(defn closed-path [pts]
  (str (path pts) "Z"))

(defn points [pts]
  (transduce
    (comp
      (map (fn [[x y]]
             (str x \, y)))
      (interpose " "))
    str pts))
