(ns handwritten-digits.core)

(ns handwritten-digits.util
  (:use [handwritten-digits.mnist :as mnist]
        [incanter.core])
  (:require [gloss.io]
            [clojure.java.io :as io]))

(defn read-bytes [file] 
  (with-open [input (io/input-stream file)
              output (new java.io.ByteArrayOutputStream)]
    (io/copy input output)
    (.toByteArray output)))

(defn read-labels [file] (gloss.io/decode label-codec (read-bytes file)))
(defn read-images [file] (gloss.io/decode image-codec (read-bytes file)))

(defn t-labels []
  (let [labels (:labels (read-labels "data/train-labels-idx1-ubyte"))]
    (to-dataset (range 1 (count labels)) labels)))

(defn t-images []
  (let [images (:images (read-images "data/train-images-idx3-ubyte"))]
    (to-dataset (range 1 (count images)) images)))

;; pass training function in to generic training alg?

(defn view-labels [labels] (with-data labels (view $data)))

