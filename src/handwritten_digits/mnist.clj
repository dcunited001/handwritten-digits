(ns handwritten-digits.mnist
  (:use [gloss core io]
        ;;[incanter core stats charts]
        ))

;; TODO: change header to validate magic-numbers
(def mnist-images-magic-num 2051)
(def mnist-labels-magic-num 2049)

(defcodec mnist-label-header (ordered-map :magic :uint32-be 
                                          :num :uint32-be))

(defcodec mnist-image-header (ordered-map :magic :uint32-be
                                          :num :uint32-be
                                          :rows :uint32-be
                                          :cols :uint32-be))

(defcodec mnist-labels
  (let [
        h->b (fn [head] (finite-frame (:num head) (ordered-map :label :ubyte)))
        b->h (fn [body] (ordered-map :magic mnist-labels-magic-num
                                     :num (count body)))
        ]
    (header (ordered-map :magic (enum :uint32-be mnist-labels-magic-num)
                         :num :uint32-be) h->b b->h)))

;;(defcodec mnist-labels (compile-frame mnist-label-header))
;;(defcodec mnist-images-header (enum :uint32-be))
