(ns handwritten-digits.test.core
  (:require [clojure.test :refer :all]
            [handwritten-digits.core :refer :all]))

(ns handwritten-digits.test.mnist-digits
  (:use [handwritten-digits mnist-digits util]

  (def mnist-images-magic-num-byte-arr '(0x00 0x00 0x80 0x03))
  (def mnist-labels-magic-num-byte-arr '(0x00 0x00 0x80 0x01))

  
))


