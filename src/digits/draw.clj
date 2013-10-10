(ns digits.draw
  (:import (java.awt.image BufferedImage WritableRaster)
           (javax.imageio ImageIO)
           (java.io File IOException))
  (:gen-class))

(defn new-buffered-image [sizex sizey] (BufferedImage. sizex sizey BufferedImage/TYPE_BYTE_GRAY))

(defn digit-image [data sizex sizey]
  (let [img (new-buffered-image sizex sizey)]
    (-> (.getRaster img)
        (.setPixels 0 0 sizex sizey (int-array data)))
    img))

(defn digits-image [data sizex sizey nx ny]
  (let [img (new-buffered-image (* nx sizex) (* ny sizey))
        raster (.getRaster img)]
    (doseq [c (range nx)
            r (range ny)]
      ;;(prn (str "C: " c " R: " r))
      (let [n (+ c (* r ny))
            offx (* c sizex)
            offy (* r sizey)
            d (nth data n [])]
        
        (if (> (count d) 0)
          ;; FIXME: can't print theta2 with 5 5 5 2, but can print with 5 5 5 5
          (. raster setPixels offy offx sizex sizey (int-array d)))
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

