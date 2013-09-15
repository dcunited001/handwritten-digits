(ns handwritten-digits.mnist
  (:use [gloss core io]
        ;;[incanter core stats charts]
        ))

;; TODO: change header to validate magic-numbers
(def mnist-images-magic-num 2051)
(def mnist-labels-magic-num 2049)

(defcodec mnist-label-header 
  (ordered-map :magic :uint32-be 
               :num :uint32-be))

(defn mnist-label-header->body [head]
  (compile-frame (ordered-map :labels (repeated :byte :prefix :none))))

(defn mnist-label-body->header [body] 
  (ordered-map :magic mnist-labels-magic-num 
               :num (count body)))

(defcodec mnist-labels-codec
  (header mnist-label-header mnist-label-header->body mnist-label-body->header))


(defcodec mnist-image-header 
  (ordered-map :magic :uint32-be
               :num :uint32-be
               :rows :uint32-be
               :cols :uint32-be))
