(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util])
)

;; TODO: protocol for different algorithms

;; repaint

(defn paint [& images]
  (fn [c g] 
    (.drawImage g @(first images) 0 0 nil)
    ;; draw second image
    )
)

(defn init-theta1 [num-units sizex sizey]
  (let [mu 0 sigma 1]
    ((m/rnorm mu sigma (* sizex sizey) num-units)))
)

(defn init-theta2 [num-digits num-units]
  ;; TODO add bias unit
  ;;  (rand (* num-units) num-digits)
)

(defn add-bias-unit [matrix]
  (m/hstack (m/+ 1 (m/zeros (m/nrows matrix) 1)) matrix))

(defn remove-bias-unit [matrix]
  (m/get matrix (range 0 (m/nrows matrix)) (range 1 (m/ncols matrix))))

