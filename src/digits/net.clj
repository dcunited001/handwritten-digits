(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util])
)

;; TODO: protocol for different algorithms

;; repaint

(defn paint [& images]
  (fn [c g]
    (.drawImage g @(first images) 0 0 nil)
    ;; draw second image
    )
)

(defn add-bias-unit [matrix]
  (m/hstack (m/+ 1 (m/zeros (m/nrows matrix) 1)) matrix))

(defn remove-bias-unit [matrix]
  (m/get matrix (range 0 (m/nrows matrix)) (range 1 (m/ncols matrix))))

(defn init-theta1 [num-units sizex sizey]
  (let [mu 0 sigma 1]
    (add-bias-unit (m/rnorm mu sigma (* sizex sizey) num-units))))

(defn init-theta2 [num-digits num-units]
  (let [mu 0 sigma 1]
    (add-bias-unit (m/rnorm mu sigma num-digits num-units))))

(defn convert-to-image-seq 
  "Preps a theta matrix for printing 
    by removing the bias unit 
    and scaling all values to 0-255"
  [matrix] 
  (remove-bias-unit matrix)
  )

(defn sigmoid 
  "Runs the sigmoid function against values in the matrix"
  [z]
  (m/div 1 (m/+ 1 (m/exp (m/mult -1 z)))))

(defn sigmoid-gradient 
  "Calculates the sigmoid gradient for values in a matrix.
     Assumes the matrix has a domain within 0 and 1 (sigmoid output)"
  [z]
  (m/mult z (m/- z 1)))

