(ns digits.core
  (:use [incanter core charts])
  (:require [digits.util :as util]
            [digits.mnist :as mnist]
            [digits.net :as net]
            [digits.draw :as draw])
  (:gen-class :main true))

(defn generic-chart [sizex sizey] (function-plot sin sizex sizey))

(defn -main [& args]
  (let [sizex 28
        sizey 28
        labels (:labels (util/read-labels "data/train-labels-idx1-ubyte"))
        images (:images (util/read-images "data/train-images-idx3-ubyte"))
        digits-chart (generic-chart (* sizex 10) (* sizey 10))]

    (doto digits-chart
      (add-image 0 0 (draw/digit-image (head images) sizex sizey))
      view)

    ;; (let [digits-img (draw/create-image 280 280)
    ;;       canvas (draw/make-canvas)
    ;;       frame (draw/make-frame 280 280 canvas)
    ;;       ]
    ;; (draw/show-frame (draw/make-frame 280 280))
    ;; (draw/draw-digits-on digits-img 
    ;;                      (take 100 images)
    ;;                      28 28 10 10)    
    ;; )

    ;; the network has 
    ;; - an input layer with 784 inputs (28*28)
    ;; - a hidden layer with 25 units
    ;; - an output layer with 10 units
    ;; - input matrix ((784+1) x n) n=batch size
    ;; - theta1 matrix (25 x (784+1))
    ;; - theta2 matrix (10 x (25+1))

))
