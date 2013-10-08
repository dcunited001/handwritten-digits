(ns digits.util
  (:use [digits.mnist :as mnist])
  (:require [gloss.io]
            [clojure.java.io :as io]))

(defn read-bytes [file] 
  (with-open [input (io/input-stream file)
              output (new java.io.ByteArrayOutputStream)]
    (io/copy input output)
    (.toByteArray output)))

(defn read-labels [file] (gloss.io/decode label-codec (read-bytes file)))
(defn read-images [file] (gloss.io/decode image-codec (read-bytes file)))

;;(defn view-labels [labels] (with-data labels (view $data)))
