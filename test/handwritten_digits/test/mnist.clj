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
  (let [magic labels-magic-num
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
        magic labels-magic-num
        n 4]
    (is (= (decoded :num) n))
    (is (= (decoded :magic) magic))
    ))

(deftest image-header-decode-test
  (let [decoded (gloss.io/decode image-header image-header-bytes)
        magic images-magic-num
        n 2
        r 4
        c 4]
    (is (= (decoded :magic) magic))
    (is (= (decoded :num) n))
    (is (= (decoded :rows) r))
    (is (= (decoded :cols) c))
    ))

;; ;; FIXME: y u no defcodec
;; (deftest labels-data-encode-test 
;;   (let [labels '(1 2 3 4)
;;         encoded (gloss.io/encode label-header labels)]
;;   (is (= (encoded :labels) '(1 2 3 4)))
;;   ))

(deftest labels-data-decode-test
  (let [decoded (gloss.io/decode labels-codec label-bytes)]
    (is (= (decoded :labels) '(1 2 3 4)))))

(deftest labels-data-encode-test
  (let [encoded (gloss.io/encode labels-codec {:labels '(1 2 3 4)})]
    (prn encoded) ;; TODO: test this properly
    ;;(prn (byte-count encoded)) ;; y u no byte-count (need convert-char-seqs from gloss tests)
))

(deftest images-data-decode-test
  (let [decoded (gloss.io/decode images-codec label-bytes)]
    (is (= (decoded :labels) '(1 2 3 4)))))


