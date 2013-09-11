(ns handwritten-digits.core)

(ns handwritten-digits.util)

(defn make-byte-array [s] 
  (byte-array (map #(byte %) s)))

(defn byte-to-signed [b]
  (- b 128))

(defn make-unsigned-byte-array [s]
  (map byte-to-signed (make-byte-array s))
 
(ns handwritten-digits.mnist-digits
  (:use [gloss core io]
        [incanter core stats charts])

  (def mnist-images-magic-num 2051)
  (def mnist-labels-magic-num 2049)

  ;;(defcodec mnist-images-header (enum :uint32-be))

)

;; reading mnist 
;; - http://csi701-group2.googlecode.com/svn-history/r13/trunk/populate-db/clojure/src/populate_db_clojure/core.clj

;; clojuresphere
;; - gloss    http://www.clojuresphere.com/gloss/gloss
;; - incanter http://www.clojuresphere.com/incanter/incanter

;; incanter api
;; - http://liebke.github.io/incanter/branch-master/index.html

;; incanter examples (matrices & statistics)
;; - https://github.com/liebke/incanter/wiki/Matrices
;; - https://github.com/liebke/incanter/wiki/Statistics-Examples

;; incanter cheatsheet
;; - http://incanter.org/docs/incanter-cheat-sheet.pdf

;; gloss examples
;; - opentoken https://github.com/mch/opentoken
;; - post   http://comments.gmane.org/gmane.comp.java.clojure.aleph/21
;; - codecs https://github.com/andrewvc/engulf/blob/master/src/engulf/comm/netchan.clj
;; - tests  https://github.com/andrewvc/engulf/blob/master/test/engulf/test/comm/netchan_test.clj
