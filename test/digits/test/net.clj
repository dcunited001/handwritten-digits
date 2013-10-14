(ns digits.test.net
  (:require [clojure.test :refer :all]
            [clatrix.core :as m])
  (:use [digits.net]))

(def matr (m/matrix [[0 1 2 3]
                     [4 5 6 7]]))

(deftest sigmoid-test
  (let [sig (sigmoid matr)]
    (is (= 0.5 (m/get sig 0 0)))))

(deftest sigmoid-gradient-test
  (let [sig (sigmoid matr)
        sig-gr (sigmoid-gradient sig)]
    (is (= 0.25 (m/get sig-gr 0 0)))))

(deftest add-bias-unit-test
  (let [added (add-bias-unit matr)]
    (is (= (m/ncols added) (+ 1 (m/ncols matr))))
    (is (= 1.0 (m/get added 0 0)))))

(deftest remove-bias-unit-test
  (let [rm (remove-bias-unit matr)]
    (is (= (m/ncols rm) (+ -1 (m/ncols matr))))))

(deftest labels-to-binary-matrix-test
  (let [labels (m/matrix [0 1 2 3 4])]
    (is (= (labels-to-binary-matrix 5 labels) (m/eye 5)))))

(deftest unroll-to-vec-test
  (let [mat (m/matrix [[1 5][2 6][3 7][4 8]])]
    (is (= (unroll-to-vec mat) (m/t (m/matrix [1 2 3 4 5 6 7 8]))))))
