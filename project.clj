(defproject digits "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [seesaw "1.4.3"]
                 [incanter "1.5.4"]
                 [gloss "0.2.2"]]
  :jvm-opts ["-Xmx1g"]
  :main digits.core)

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

;; clojure and seesaw (swing)
;; - http://daveray.github.io/seesaw/seesaw.graphics-api.html
;; - http://icyrock.com/blog/2012/01/clojure-and-seesaw/
;; - https://github.com/daveray/regenemies/blob/master/src/regenemies/ui.clj

