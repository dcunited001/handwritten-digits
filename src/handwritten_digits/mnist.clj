(ns handwritten-digits.mnist
  (:use [gloss core io]
        ;;[incanter core stats charts]
        ))

;; TODO: change header to validate magic-numbers
(def images-magic-num 2051)
(def labels-magic-num 2049)

(defcodec label-header 
  (ordered-map :magic :uint32-be 
               :num :uint32-be))

(defn label-header->body [head]
  (compile-frame (ordered-map :labels (repeated :byte :prefix :none))))

(defn label-body->header [body] 
  {:magic labels-magic-num 
   :num (count (body :labels)) })

  ;; FIXME: why does this fail when i use an ordered-map?
  ;; (ordered-map :magic labels-magic-num 
  ;;              :num (count body)))

(defcodec labels-codec
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
    (ordered-map :labels 
                 (repeated (image-frame 
                            (head :rows) 
                            (head :cols)))))))

(defn image-body->header [body]
  
)

;; (defcodec images-codec
;;   (header)
;; )
