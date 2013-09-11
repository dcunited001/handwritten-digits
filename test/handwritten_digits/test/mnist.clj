(ns handwritten-digits.test.mnist
  (:require [clojure.test :refer :all]
            [gloss core io])
  (:use [handwritten-digits mnist util]))

  ;; (def mnist-images-magic-num-byte-arr '(0x00 0x00 0x80 0x03))
  ;; (def mnist-labels-magic-num-byte-arr '(0x00 0x00 0x80 0x01))

  (def labels-data {:magic '(0x00 0x00 0x80 0x01)
                    :num '(0x00 0x00 0x00 0x04)
                    :labels '(0x01 0x02 0x03 0x04)})


  ;; FIXME: y u no defcodec
  (deftest labels-data-encode-test 
    (let [labels '(1 2 3 4)
          encoded (gloss.io/encode mnist-label-header labels)]
    (is (= (encoded :labels) '(1 2 3 4)))
    ))

(deftest labels-data-decode-test
  (let [labels-bin-data '()]) ;; TODO: need to set up byte array
 )

