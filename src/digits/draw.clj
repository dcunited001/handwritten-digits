(ns digits.draw
  (:import (java.awt.image BufferedImage WritableRaster)
           (javax.imageio ImageIO)
           (java.io File IOException))
  (:gen-class))

(defn new-buffered-image [sizex sizey] (BufferedImage. sizex sizey BufferedImage/TYPE_BYTE_GRAY))

(defn digit-image [data sizex sizey]
;;  (prn data)
  (let [img (new-buffered-image sizex sizey)]
    (-> (.getRaster img)
        (.setPixels 0 0 sizex sizey (int-array data)))
    img))

(defn digits-image [data sizex sizey nx ny]
  (let [img (new-buffered-image (* nx sizex) (* ny sizey) BufferedImage/TYPE_BYTE_GRAY)
        raster (.getRaster img)]
    (doseq [c (range nx)
            r (range ny)]
      (let [n (+ c (* r ny))
            offx (* c sizex)
            offy (* r sizey)]
        (. raster setPixels offy offx sizex sizey (int-array (nth data n)))
        ))
    img
))

(defn write-png [image filename]
  (ImageIO/write image "png" (File. filename)))

;; (gen-class :name digits.draw.Frame
;;            :prefix Frame-
;;            :extends java.swing.JFrame
;;            :main false)

;; (gen-class :name digits.draw.DigitsImage
;;            :prefix DigitsImage-
;;            :extends java.awt.image.BufferedImage
;;            :main false)

;; (defn make-canvas []
;;   (s/canvas :id :canvas :background :black :paint))

;; (defn make-frame [sizex sizey & canvas]
;;   (s/frame
;;     :title      "Digits"
;;     :on-close   :dispose
;;     :resizable? false
;;     :size       [sizex :by sizey]
;;     :content    (or canvas (make-canvas))))

;; (defn show-frame [f]
;;   (s/show! f))

;; (defn create-image [sizex sizey]
;;   (sg/buffered-image sizex sizey java.awt.image.BufferedImage/TYPE_BYTE_GRAY))

;; passed as a callback
;; (defn paint-digits-on [c g]
;;   (-> g
;;       (.drawImage ))
;;   )

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

