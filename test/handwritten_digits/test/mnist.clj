(ns handwritten-digits.test.mnist
  (:require [clojure.test :refer :all]
            [gloss core io])
  (:use [handwritten-digits mnist util]
        [gloss.data.bytes]))

(def label-bytes (read-bytes "test/data/label-data"))
(def label-header-bytes (read-bytes "test/data/label-header"))

(def image-bytes (read-bytes "test/data/image-data"))
(def image-body-bytes (read-bytes "test/data/image-body"))
(def image-header-bytes (read-bytes "test/data/image-header"))

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

(deftest label-data-decode-test
  (let [decoded (gloss.io/decode label-codec label-bytes)]
    (is (= (decoded :labels) '(1 2 3 4)))))

(deftest label-data-encode-test
  (let [encoded (gloss.io/encode label-codec {:labels '(1 2 3 4)})]
    (prn encoded) ;; TODO: test this properly
    ;;(prn (byte-count encoded)) ;; y u no byte-count (need convert-char-seqs from gloss tests)
))

(deftest image-data-decode-test
  (let [decoded (gloss.io/decode image-codec image-bytes)]
    (is (= (decoded :magic) image-magic-num))
    (is (= (decoded :num) 2))
    (is (= (decoded :rows) 4))
    (is (= (decoded :cols) 4))
    (is (= (count (decoded :images)) 2))
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

(deftest image-data-encode-test)
