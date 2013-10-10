(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util]
            [digits.draw :as draw]))

;; TODO: protocol for different algorithms

(defn paint [num-processed & images]
  (fn [c g]
    (.drawImage g @(first images) 0 0 nil)
    (.drawImage g @(second images) 300 0 nil)
    (.drawImage g @(nth images 2) 300 150 nil)
    (.drawString g (str @num-processed) 300 280)
))

(defn add-bias-unit [matrix]
  (m/hstack (m/+ 1 (m/zeros (m/nrows matrix) 1)) matrix))

(defn remove-bias-unit [matrix]
  (m/get matrix (range 0 (m/nrows matrix)) (range 1 (m/ncols matrix))))

(defn init-theta [nr nc mu sigma]
  (m/rnorm mu sigma nr (+ 1 nc)))

(defn convert-to-img-seq 
  "Preps a theta matrix for printing 
    by removing the bias unit 
    and scaling all values to 0-255"
  [mat]
  ;; FIXME: scale with (* 255 (sigmoid mat))
  (m/as-vec (remove-bias-unit mat))
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
  [batch-size sizex sizey nx ny num-processed-atom
   & img-atoms]
  
  (fn process [lambda img lbl t1 t2]
    (if (> (count lbl) 0 )
      (let [remaining (count lbl)
            input (take batch-size img)
            X (add-bias-unit (m/matrix input))
            y (take 100 (m/matrix (take batch-size lbl)))]
        ;; currently each iteration takes between 20-30 ms (just init/draw input/theta1)
        (do (reset! (first img-atoms) (draw/digits-image input sizex sizey nx ny))
            (reset! (second img-atoms) (draw/digits-image (convert-to-img-seq t1) sizex sizey 5 5))
            (reset! (nth img-atoms 2) (draw/digits-image (convert-to-img-seq t2) 5 5 5 5))

            (-> (java.util.Date.) prn)
            (prn @num-processed-atom)
            (swap! num-processed-atom + batch-size)
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
    
