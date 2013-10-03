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
    (println (count (take 5 images)))
    (println (inc/to-matrix (inc/to-dataset (take 5 images))))
    (println (inc/sel (inc/to-matrix (inc/to-dataset (take 5 images))) :cols 0))

    (draw/draw-100-digits images)
))
