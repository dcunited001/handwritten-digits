(ns digits.net
  (:require [clatrix.core :as m]
            [digits.util :as util])
)

;; TODO: protocol for different algorithms

;; repaint

(defn paint [c g]
  
)

(defn train [images labels & data]

)

(defn init-theta1 [num-units sizex sizey]
  ;; TODO add bias unit
  (rand (* sizex sizey) num-units)
)

(defn init-theta2 [num-digits num-units]
  ;; TODO add bias unit
  (rand (* num-units) num-digits)
)


