(ns handwritten-digits.test.mnist
  (:require [clojure.test :refer :all]
            [gloss core io])
  (:use [handwritten-digits mnist util]
        [gloss.data.bytes]))

(def label-bytes (read-bytes "test/data/labels-data"))
(def label-header-bytes (read-bytes "test/data/labels-header"))

(def image-bytes (read-bytes "test/data/images-data"))
(def image-header-bytes (read-bytes "test/data/images-header"))

(deftest label-header-encode-test
  (let [magic label-magic-num
        n 2
        encoded (gloss.io/encode label-header {:magic magic :num n})
        bytes label-header-bytes]
    ;; TODO: figure out interface to object returned by gloss.io/encode 
    ;; (gloss.data.bytes.core.SingleBufferSequence or HeapByteBuffer)
    ;;(is (= (first encoded) (first bytes)))
    ;;(is (= (last encoded) (last bytes)))
    ))

(deftest label-header-decode-test 
  (let [decoded (gloss.io/decode label-header label-header-bytes)
        magic label-magic-num
        n 4]
    (is (= (decoded :num) n))
    (is (= (decoded :magic) magic))
    ))

(deftest image-header-decode-test
  (let [decoded (gloss.io/decode image-header image-header-bytes)
        magic image-magic-num
        n 2
        r 4
        c 4]
    (is (= (decoded :magic) magic))
    (is (= (decoded :num) n))
    (is (= (decoded :rows) r))
    (is (= (decoded :cols) c))
    ))

;; ;; FIXME: y u no defcodec
;; (deftest label-data-encode-test 
;;   (let [label '(1 2 3 4)
;;         encoded (gloss.io/encode label-header label)]
;;   (is (= (encoded :label) '(1 2 3 4)))
;;   ))

(deftest label-data-decode-test
  (let [decoded (gloss.io/decode label-codec label-bytes)]
    (is (= (decoded :label) '(1 2 3 4)))))

(deftest label-data-encode-test
  (let [encoded (gloss.io/encode label-codec {:label '(1 2 3 4)})]
    (prn encoded) ;; TODO: test this properly
    ;;(prn (byte-count encoded)) ;; y u no byte-count (need convert-char-seqs from gloss tests)
))

(deftest image-data-decode-test
  (let [decoded (gloss.io/decode image-codec label-bytes)]
    (is (= (decoded :label) '(1 2 3 4)))))


