(ns handwritten-digits.draw
  (:use [incanter core charts])
  (:require [seesaw.core :as s]
        [seesaw.graphics :as sg]
        [seesaw.color :as scolor])
  (:gen-class))

;; incanter and awk images: http://pastebin.com/sYn517qh
;; - https://github.com/joeatwork/nma.simple/blob/master/src/nma/demo.clj

(defn make-canvas []
  (s/canvas :id :canvas :background :black))

(defn make-frame [sizex sizey]
  (s/frame
    :title      "Digits"
    :on-close   :dispose
    :resizable? false
    :size       [sizex :by sizey]
    :content    (make-canvas)))

(defn show-frame [f]
  (s/show! f))

(defn create-image [sizex sizey]
  (sg/buffered-image sizex sizey sg/buffered-image/TYPE_BYTE_GRAY))

(defn draw-digits-on [img data sizex sizey nx ny]
  (let [raster (. img getRaster)]
    (doseq [c (range (- nx 1))
            r (range (- ny 1))
            m (range (* sizex sizey))]
      (let [n (+ c (* r ny))
            ix (mod m sizex)
            iy (quot m sizey)
            x (+ ix (* c sizex))
            y (+ iy (* r sizey))])
      (. raster setPixel y x (float-array 1 (sel data n m)))))

(defn paint-digits-on [img cv] 
  
  )

;; (defexample []
;;   (frame 
;;     :title "Canvas Example" 
;;     :width 500 :height 300
;;     :content 
;;     (border-panel :hgap 5 :vgap 5 :border 5
;;                   ; Create the canvas with initial nil paint function, i.e. just canvas
;;                   ; will be filled with it's background color and that's it.
;;                   :center (canvas :id :canvas :background "#BBBBDD" :paint nil)

;;                   ; Some buttons to swap the paint function
;;                   :south (horizontal-panel :items ["Switch canvas paint function: "
;;                                                    (switch-paint-action "None" nil)
;;                                                    (switch-paint-action "Rectangles" paint1)
;;                                                    (switch-paint-action "Ovals" paint2)]))))

  ;; (frame
  ;;   :title "Gaidica"
  ;;   :size  [600 :by 600]
  ;;   :on-close :exit
  ;;   :menubar (menubar :items [(menu :text "View" :items [(menu-item :class :refresh)])])
  ;;   :content (border-panel
  ;;              :border 5
  ;;              :hgap 5
  ;;              :vgap 5
  ;;              :north  (make-toolbar)
  ;;              :center (make-tabs)
  ;;              :south (label :id :status :text "Ready")))

;;buffered-image sizex sizey

;; apparently this isnt going to work (very easily)
;; - heat-map doesn't index [x y] nicely as integers =/

;; visualizing this is going to require
;; - creating java.awt.Image with matrix data
;; - instantiating a dummy chart (or creating a manual JFreeChart)
;; - using add-image to manually add the image to the chart.

;; (defn get-pixel [data x y sizex sizey nx ny]
;;   ;; translate 2-d coordinates 
;;   ;;   to retrieve from list of unwrapped matrices
;;   (let [n (+ (quot x sizex) (* nx (quot y sizey)))
;;         m (+ (mod  x sizex) (* sizex (mod y sizey)))]
;;     (sel data m n)))

;; (defn create-digits-image [data sizex sizey nx ny]
;;   ;;(let [img ])
;; )

;; (defn draw-digits [data sizex sizey nx ny]
;;   (let [xmax (- (* sizex nx) 1)  
;;         ymax (- (* sizey ny) 1)]
;;     (binding [sizex sizex
;;               sizey sizey
;;               nx nx
;;               data data]
;;       (heat-map get-pixel 0 xmax 0 ymax
;;                 :title "digits" 
;;                 :color false))))

;; (defn draw-100-digits [data] 
;;   (draw-digits (to-matrix (to-dataset (take 100 data))) 28 28 10 10))

