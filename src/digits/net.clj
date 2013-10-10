(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util]
            [digits.draw :as draw]))

;; TODO: protocol for different algorithms

(defn paint [num-processed & img-atoms]
  (fn [c g]
    (.drawImage g @(first img-atoms) 0 0 nil)
    (.drawImage g @(second img-atoms) 300 0 nil)
    (.drawImage g @(nth img-atoms 2) 300 150 nil)
    (.drawString g (str @num-processed) 300 280)
))

(defn add-bias-unit [matrix]
  (m/hstack (m/+ 1 (m/zeros (m/nrows matrix) 1)) matrix))

(defn remove-bias-unit [matrix]
  (m/get matrix (range 0 (m/nrows matrix)) (range 1 (m/ncols matrix))))

(defn init-theta [nr nc mu sigma]
  (m/rnorm mu sigma nr (+ 1 nc)))

(defn sigmoid 
  "Runs the sigmoid function against values in the matrix"
  [z]
  (m/div 1 (m/+ 1 (m/exp (m/mult -1 z)))))

(defn sigmoid-gradient 
  "Calculates the sigmoid gradient for values in a matrix.
     Assumes the matrix has a domain within 0 and 1 (sigmoid output)"
  [z]
  (m/mult z (m/- 1 z)))

(defn convert-to-img-seq 
  "Preps a theta matrix for printing 
    by removing the bias unit 
    and scaling all values to 0-255"
  [mat]
  ;; FIXME: scale with (* 255 (sigmoid mat))
  (m/as-vec (remove-bias-unit (m/* 255.0 (sigmoid mat))))
)

(defn labels-to-binary-matrix [num-labels lbls]
  (let [one-to-k (m/+ 1 (m/zeros (m/nrows lbls) num-labels))]
    (m/map-indexed (fn [i j v] 
                     (if (= j (int (m/get lbls i 0))) 
                       1 0))
                   one-to-k)))

(defn process-net
  "Returns a function that can process data
    m - batch size
    img-atoms references of image atoms to update"
  [batch-size num-labels sizex sizey nx ny num-processed-atom
   & img-atoms]
  
  (fn process [lambda img lbl t1 t2]
    (if (> (count lbl) 0 )
      (let [remaining (count lbl)
            input (take batch-size img)
            X (add-bias-unit (m/matrix input))
            y (m/matrix (take batch-size lbl))
            Y (labels-to-binary-matrix num-labels y)]

        ;; currently each iteration takes between 20-30 ms (just init/draw input/theta1)
        (do (reset! (first img-atoms) (draw/digits-image input sizex sizey nx ny))
            (reset! (second img-atoms) (draw/digits-image (convert-to-img-seq t1) sizex sizey 5 5))
            (reset! (nth img-atoms 2) (draw/digits-image (convert-to-img-seq t2) 5 5 5 5))

            (let [z2 (m/* X (m/t t1))
                  a2 (add-bias-unit (sigmoid z2))
                  z3 (m/* a2 (m/t t2))
                  a3 (sigmoid z3)
                  ones (m/+ 1 (m/zeros num-labels 1))
                  k-ones (m/+ 1 (m/zeros batch-size 1))
                  cost (/
                        (+
                         (-> (m/mult Y (m/log a3)) ;;gets sum when incorrect
                             (m/* ones)
                             (m/t)
                             (m/* k-ones)
                             (m/* -1)
                             (m/get 0 0))
                         (-> (m/- 1 Y)
                             (m/mult (m/log (m/- 1 a3)))
                             (m/* ones)
                             (m/t)
                             (m/* k-ones)
                             (m/* -1)
                             (m/get 0 0)))
                        (double batch-size))
                  ]
     
            (prn cost)
)
;; cost = (-sum((Y .* log(A3')) * Ones) - sum(((1-Y) .* log(1-A3')) * Ones))/m;

            (swap! num-processed-atom + batch-size)
            
            ;;(-> (java.util.Date.) prn)
            ;;(prn @num-processed-atom)
            (recur lambda (drop 100 img) (drop 100 lbl) t1 t2))
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
    
