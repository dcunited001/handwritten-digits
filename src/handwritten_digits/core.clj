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

    ;;(draw/draw-100-digits images)
))
