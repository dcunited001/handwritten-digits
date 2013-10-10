(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util]))

;; TODO: protocol for different algorithms

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
  (m/mult z (m/- 1 z)))

(defn process-net
  "Returns a function that can process data
    m - batch size
    img-atoms references of image atoms to update"
  [batch-size sizex sizey nx ny 
   & img-atoms]
  
  (fn process [lambda img lbl t1 t2]
    (let [remaining (count lbl)
          input (take batch-size img)
          X (add-bias-unit (m/matrix input))
          y (take 100 (m/matrix (take batch-size lbl)))]
      (if (> remaining 0)
        (do (reset! (first img-atoms) (draw/digits-image input sizex sizey nx ny))
            (reset! (second img-atoms) (draw/digits-image (convert-to-img-seq t1) sizex sizey 5 5))
            (process lambda (drop 100 img) (drop 100 lbl) t1 t2))
        )
      )
    
    ;; z(2) => theta1
    ;; a(2) => sigmoid()
    ;; @(first img-atoms) => (make-img (scale&resize a(2)))

))
    ;;
    ;; update 1st image reference
    ;; update 2nd image reference
    ;;
    
