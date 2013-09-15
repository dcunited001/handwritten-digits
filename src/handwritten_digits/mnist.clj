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
  {:magic mnist-labels-magic-num 
   :num (count (body :labels)) })

  ;; FIXME: why does this fail when i use an ordered-map?
  ;; (ordered-map :magic mnist-labels-magic-num 
  ;;              :num (count body)))

(defcodec mnist-labels-codec
  (header mnist-label-header mnist-label-header->body mnist-label-body->header))

(defcodec mnist-image-header 
  (ordered-map :magic :uint32-be
               :num :uint32-be
               :rows :uint32-be
               :cols :uint32-be))

(defn mnist-image-frame [r c]
  (compile-frame (finite-frame (* r c) (repeated :byte :prefix :none))))

(defn mnist-image-header->body [head]
  (compile-frame 
   (finite-frame 
    (head :num) 
    (ordered-map :labels 
                 (repeated (mnist-image-frame 
                            (head :rows) 
                            (head :cols)))))))

;; (defn mnist-image-body->header [body]

;; )

;; (defcodec mnist-images-codec
;;   (header)
;; )
