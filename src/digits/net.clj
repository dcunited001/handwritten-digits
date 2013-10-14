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
    (.setColor g (java.awt.Color/GREEN))
    (.drawString g (str "Training: " @num-processed) 300 280)
))

(defn ones [r c]
  (m/+ 1 (m/zeros r c)))

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
  (let [one-to-k (ones (m/nrows lbls) num-labels)]
    (m/map-indexed (fn [i j v] 
                     (if (= j (int (m/get lbls i 0))) 
                       1 0))
                   one-to-k)))

(defn regularize-theta 
  "Takes a theta matrix and regularizes it into a scalar"
  [t]
  (-> (m/mult t t)
      (m/* (ones (m/ncols t) 1))
      (m/* (ones 1 (m/nrows t)))
      (m/get 0 0)))

(defn cost-function 
  "Get the cost for fitting theta1/theta2 to the input labels"
  [n k lbls out]
  (let [k-ones (ones k 1)
        m-ones (ones n 1)]
    (/
     (m/get (m/+
             (-> (m/mult lbls (m/log out))
                 (m/* k-ones)
                 (m/t)
                 (m/* m-ones)
                 (m/* -1))
             (-> (m/- 1 lbls)
                 (m/mult (m/log (m/- 1 out)))
                 (m/* k-ones)
                 (m/t)
                 (m/* m-ones)
                 (m/* -1))) 0 0)
     (double n))))

(defn select-middle
  "Selects the middle of a 2d matrix"
  [mat]
  (let [nr (m/nrows mat)
        nc (m/ncols mat)]
    (m/slice mat
             (range (/ nr 3) 
                    (/ (* 2 nr) 3))
             (range (/ nc 3) 
                    (/ (* 2 nc) 3)))))

;; Wolfe Conditions
;; Polack-Ribiere flavor of conjugate gradients
;; line-search using quadratic/polynomial approximations
;; Wolfe-Powell stopping criteria & slope ratio 
;; - used for guessing initial step sizes

(defn fmincg [cost-fn]
  (let [rho 0.01   ;; rho & sigma are constants in the wolfe-powell conditions
        sig   0.5
        limit 0.1  ;; don't reevaluate within 0.1 of the limit of the current bracket
        ext   3.0  ;; extrapolate maximum 3 times the current bracket
        max   20   ;; max 20 function evaluations per line search
        ratio 100  ;; max slope ratio

        ;; with given input/theta, get an initial cost/d-theta
        ;; get cost d-theta

        ;; set search direction to be steepest
        ;; - set s to (- d-theta)
        
        ;; set d1 to -s' * s (this is the slope, apparently)
        
        ;; set z1 to red/(1-d1)

        ])
)

(defn set-images [atoms input t1 t2 sizex sizey nx ny]
  (reset! (nth atoms 0) (draw/digits-image input sizex sizey nx ny))
  (reset! (nth atoms 1) (draw/digits-image (convert-to-img-seq t1) sizex sizey 5 5))
  (reset! (nth atoms 2) (draw/digits-image (convert-to-img-seq t2) 5 5 5 5)))

(defn unroll-to-vec [mat]
  (m/reshape mat 1 (* (m/ncols mat) (m/nrows mat))))

(defn get-theta-gradient [input theta delta n λ]
  ;; at this point, theta should have the first col removed (theta-regularized)
  (let [theta-reg (m/hstack (m/zeros (m/nrows theta) 1) 
                            theta)]
  (m/+ (-> (m/t input)
           (m/* delta)
           (m/div n)
           (m/t))
       (-> (/ λ n)
           (m/mult theta-reg)))))

(defn get-cost-and-gradient [λ batch-size num-labels X y Y t1 t2]
  (let [z2 (m/* X (m/t t1))
        a2 (add-bias-unit (sigmoid z2))
        z3 (m/* a2 (m/t t2))
        a3 (sigmoid z3)

        t1-reg (remove-bias-unit t1)
        t2-reg (remove-bias-unit t2)
        
        cost (cost-function batch-size num-labels Y a3)

        reg (* (/ λ (* 2 batch-size))
               (+ (regularize-theta t1-reg)
                  (regularize-theta t2-reg)))

        cost-reg (+ cost reg)

        d3 (m/- a3 Y)

        d2 (m/mult (m/* d3 t2-reg)
                   (m/t (sigmoid-gradient (sigmoid z2))))

        t2-grad (get-theta-gradient a2 t2-reg d3 batch-size λ)
        t1-grad (get-theta-gradient X t1-reg d2 batch-size λ)
        ]
    [cost-reg t1-grad t2-grad]
))

;; TODO: 
;; - refactor cost function
;; - outline functions needed for fmincg
;; - restructure process-net into fmincg

(defn process-net
  "Returns a function that can process data
    m - batch size
    img-atoms references of image atoms to update"
  [batch-size num-labels sizex sizey nx ny num-processed-atom
   & img-atoms]
  
  (fn process [λ img lbl t1 t2]
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

                  t1-reg (remove-bias-unit t1)
                  t2-reg (remove-bias-unit t2)

                  d3 (m/- a3 Y)

                  d2 (m/mult (m/* d3 t2-reg)
                             (m/t (sigmoid-gradient (sigmoid z2))))

                  t2-grad (get-theta-gradient a2 t2-reg d3 batch-size λ)
                  t1-grad (get-theta-gradient X t1-reg d2 batch-size λ)
                  
                  cost (cost-function batch-size num-labels Y a3)

                  reg (* (/ λ (* 2 batch-size))
                         (+ (regularize-theta t1-reg)
                            (regularize-theta t2-reg)))

                  cost-reg (+ cost reg)
                  ]

            (swap! num-processed-atom + batch-size)
            
            ;;(-> (java.util.Date.) prn)
            ;;(prn @num-processed-atom)
            (recur λ (drop 100 img) (drop 100 lbl) (m/+ t1 t1-grad) (m/+ t2 t2-grad))
            )
        )
      )
    
    ;; z(2) => theta1
    ;; a(2) => sigmoid()
    ;; @(first img-atoms) => (make-img (scale&resize a(2)))

)))
    ;;
    ;; update 1st image reference
    ;; update 2nd image reference
    ;;
    
