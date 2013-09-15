(ns handwritten-digits.mnist
  (:use [gloss core io]
        ;;[incanter core stats charts]
        ))

;; TODO: change header to validate magic-numbers
(def image-magic-num 2051)
(def label-magic-num 2049)

(defcodec label-header 
  (ordered-map :magic :uint32-be 
               :num :uint32-be))

(defn label-header->body [head]
  (compile-frame (ordered-map :labels (repeated :byte :prefix :none))))

;; FIXME: ordered-map
(defn label-body->header [data] 
  {:magic label-magic-num 
   :num (count (data :labels))})

  ;; FIXME: why does this fail when i use an ordered-map?
  ;; (ordered-map :magic label-magic-num 
  ;;              :num (count body)))

(defcodec label-codec
  (header label-header label-header->body label-body->header))

(defcodec image-header 
  (ordered-map :magic :uint32-be
               :num :uint32-be
               :rows :uint32-be
               :cols :uint32-be))

(defn image-frame [r c]
  (compile-frame (finite-frame (* r c) (repeated :byte :prefix :none))))

(defn image-header->body [head]
  (compile-frame 
   (finite-frame 
    (head :num) 
    (ordered-map :images
                 (repeated (image-frame 
                            (head :rows) 
                            (head :cols)))))))

;; FIXME: ordered-map
(defn image-body->header [data]
  {:magic image-magic-num
   :num (count (data :images))
   :rows (data :rows)
   :cols (data :cols)}
)

(defcodec image-codec
  (header image-header image-header->body image-body->header))
