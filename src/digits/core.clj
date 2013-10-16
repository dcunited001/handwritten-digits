(ns digits.core
  (:require [digits.util :as util]
            [digits.mnist :as mnist]
            [digits.net :as net]
            [digits.draw :as draw]
            [digits.ui :as ui])
  (:gen-class :main true))

(defn print-time [] (-> (java.util.Date.) prn))

(defonce images (:images (util/read-images "data/train-images-idx3-ubyte")))
(defonce labels (:labels (util/read-labels "data/train-labels-idx1-ubyte")))

(def sizex 28)
(def sizey 28)

(def nx 10)
(def ny 10)
(def nlabels 10)

;; store images in atoms, then swap & render
(def img-digits (atom (draw/new-buffered-image sizex sizey)))
(def img-theta1 (atom (draw/new-buffered-image sizex sizey)))
(def img-theta2 (atom (draw/new-buffered-image sizex sizey)))

(def num-processed (atom 0))

(def running (atom true))

(defn run []
  (let [λ 1
        nexamples 10000
        theta1 (net/init-theta 25 (* sizex sizey) 0 0.12)
        theta2 (net/init-theta 10 25 0 0.12)]

    (reset! num-processed 0)
    (reset! running true)

    ;;(while @running
    (let [canvas (ui/make-canvas (net/paint num-processed img-digits img-theta1 img-theta2))
          frame (ui/make-frame (* sizex nx) (* sizey ny) canvas)]

      (-> frame
          (ui/add-behaviors)
          (ui/show-frame))

      (apply (net/process-net nexamples
                              nlabels
                              sizex sizey
                              nx ny
                              num-processed
                              img-digits img-theta1 img-theta2)
             [λ (take nexamples images) (take nexamples labels) theta1 theta2]))

    ))

(defn -main [& args]

  (print-time)
  (prn "Starting")

  (run)
  (print-time)
  (prn "Finished")

  )
