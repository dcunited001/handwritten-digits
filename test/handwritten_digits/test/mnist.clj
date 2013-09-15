(ns handwritten-digits.test.mnist
  (:require [clojure.test :refer :all]
            [gloss core io])
  (:use [handwritten-digits mnist util]))

  (defn flatten-byte-arrays [data keys] 
    (-> (reduce #(conj %1 (data %2)) '() keys) 
        flatten 
        make-unchecked-byte-array))

  (def labels-data {:magic '(0x00 0x00 0x80 0x01)
                    :num '(0x00 0x00 0x00 0x04)
                    :labels '(0x01 0x02 0x03 0x04)})

  (def images-data {:magic '(0x00 0x00 0x80 0x03)
                    :num '(0x00 0x00 0x00 0x02)
                    :rows '(0x00 0x00 0x00 0x04)
                    :cols '(0x00 0x00 0x00 0x04)
                    :image1 '(0x00 0x22 0xEE 0x00
                              0x00 0xEE 0xEE 0x00
                              0x00 0x11 0xEE 0x00
                              0x00 0x11 0xEE 0x00)
                    :image2 '(0x00 0x22 0xEE 0x00
                              0x00 0xEE 0xEE 0x00
                              0x00 0x11 0xEE 0x00
                              0x00 0x11 0xEE 0x00)
                    })

  (def label-bytes (read-bytes "test/data/labels-data"))
  (def image-bytes (read-bytes "test/data/images-data"))

  ;; FIXME: y u no defcodec
  ;; (deftest labels-data-encode-test 
  ;;   (let [labels '(1 2 3 4)
  ;;         encoded (gloss.io/encode mnist-label-header labels)]
  ;;   (is (= (encoded :labels) '(1 2 3 4)))
  ;;   ))

  (deftest labels-data-decode-test
    (let [decoded (gloss.io/decode
                   mnist-label-header 
                   label-bytes)]
      (is (= (decoded :labels) '(1 2 3 4)))))
    
  (deftest a-test 
    (prn (map str label-bytes)))

