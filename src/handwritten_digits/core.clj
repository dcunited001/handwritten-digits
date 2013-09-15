(ns handwritten-digits.core)

(ns handwritten-digits.util
  (:use [clojure.java.io :as io]))

(defn read-bytes [file] 
  (with-open [input (io/input-stream file)
              output (new java.io.ByteArrayOutputStream)]
    (io/copy input output)
    (.toByteArray output)))
 
