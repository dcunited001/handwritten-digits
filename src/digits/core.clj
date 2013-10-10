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
  (let [lambda 0.05
        theta1 (net/init-theta 25 (* sizex sizey) 0 0.1)
        theta2 (net/init-theta 10 25 0 0.1)]

    (reset! num-processed 0)
    (reset! running true)

    ;;(while @running
    (let [canvas (ui/make-canvas (net/paint num-processed img-digits img-theta1 img-theta2))
          frame (ui/make-frame (* sizex nx) (* sizey ny) canvas)]
      (-> frame
          (ui/add-behaviors)
          (ui/show-frame))

      (apply (net/process-net (* nx ny) 
                              nlabels
                              sizex sizey 
                              nx ny 
                              num-processed 
                              img-digits img-theta1 img-theta2) 
             [lambda images labels theta1 theta2]))

    ;; (reset! running true)
    ;; (while @running 
    ;;   (dotimes [i PERIODS] 
    ;;     (when @running
    ;;       (show (frame i) :title "Mobile Activity in Singapore" :on-close #(reset! running false))
    ;;       (Thread/sleep 40))))

))

(defn -main [& args]

  (print-time)
  (prn "Starting")

  (run)
  (print-time)
  (prn "Finished")

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

)
