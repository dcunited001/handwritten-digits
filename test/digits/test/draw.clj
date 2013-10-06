(ns digits.test.draw
  (:require [clojure.test :refer :all])
  (:use [digits draw]))

(deftest digit-image-test
  (let [data [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15]
        sizex 4
        sizey 4]
    (digit-image data sizex sizey))
)
