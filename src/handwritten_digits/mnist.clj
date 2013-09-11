(ns handwritten-digits.mnist
  (:use [gloss core io]
         ;;[incanter core stats charts]
         ))

  (def mnist-images-magic-num 2051)
  (def mnist-labels-magic-num 2049)

  (defn mnist-magic-num-header [magic] (enum :uint32-be {:hdr magic}))

  (defn mnist-label-header []
    (let [
          h->b (fn [head] (compile-frame 
                           (finite-frame (head :num)
                                         [:label :ubyte])))
          b->h (fn [body] [mnist-labels-magic-num
                           (count body)])
          ]
    (header (compile-frame [:magic (mnist-magic-num-header mnist-labels-magic-num)
                            :num :uint32-be]) h->b b->h)))

  ;;(defcodec mnist-labels (compile-frame mnist-label-header))
  ;;(defcodec mnist-images-header (enum :uint32-be))
