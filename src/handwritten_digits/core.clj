(ns handwritten-digits.core
  (:require [handwritten-digits.util :as util]
            [handwritten-digits.mnist :as mnist]
            [handwritten-digits.net :as net]
            [handwritten-digits.draw :as draw]
            [incanter.core :as inc])
  (:gen-class :main true))

(defn -main [& args]
  (let [labels (:labels (util/read-labels "data/train-labels-idx1-ubyte"))
        images (:images (util/read-images "data/train-images-idx3-ubyte"))]
    (println args)

    (draw/show-frame (draw/make-frame 280 280))

    ;; the network has 
    ;; - an input layer with 784 inputs (28*28)
    ;; - a hidden layer with 25 units
    ;; - an output layer with 10 units
    ;; - theta1 matrix (25 x (784+1))
    ;; - theta2 matrix (10 x (25+1))

))
