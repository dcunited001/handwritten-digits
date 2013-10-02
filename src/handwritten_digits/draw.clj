(ns handwritten-digits.draw
  (:use [incanter core charts]))

(defn draw-digits [data x-size y-size nx ny]
  (let [xmax (* x-size nx) 
        ymax (* y-size ny)]
  (heat-map get-pixel 0 0 xmax ymax
            :title "digits" 
            :color false)))

(defn get-pixel [x y]
  ;; translate 2-d coordinates 
  ;;   to retrieve from list of unwrapped matrices
  (let [n (+ (quot x size-x) (* nx (quot y size-y)))
        m (+ (mod  x size-x) (* size-x (mod y size-y)))]
    (sel data n m)))

