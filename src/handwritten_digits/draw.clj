(ns handwritten-digits.draw
  (:use [incanter core charts])
  (:gen-class))

(declare ^:dynamic sizex)
(declare ^:dynamic sizey)
(declare ^:dynamic nx)
(declare ^:dynamic ny)
(declare ^:dynamic data)

(defn get-pixel [x y]
  ;; translate 2-d coordinates 
  ;;   to retrieve from list of unwrapped matrices
  (let [n (+ (quot x sizex) (* nx (quot y sizey)))
        m (+ (mod  x sizex) (* sizex (mod y sizey)))]
    (prn (str "X=" x " Y=" y " M=" m " N=" n))
    (sel data m n)))

(defn draw-digits [data sizex sizey nx ny]
  (let [xmax (- (* sizex nx) 1)  
        ymax (- (* sizey ny) 1)]
    (binding [sizex sizex
              sizey sizey
              nx nx
              ny ny
              data data]
      (heat-map get-pixel
                0 xmax 0 ymax
                :title "digits" 
                :color false))))

  ;; (heat-map get-pixel 0 0 xmax ymax
  ;;           :title "digits" 
  ;;           :color false)))

(defn draw-100-digits [data] 
  (draw-digits (to-matrix (to-dataset (take 100 data))) 28 28 10 10))

