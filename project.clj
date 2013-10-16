(defproject digits "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 ;;[clatrix "0.3.0"] ;; loading from dcunited001/clatrix
                 [slingshot "0.10.3"]
                 [org.jblas/jblas "1.2.3"]
                 [net.mikera/core.matrix "0.7.2"]

                 [seesaw "1.4.3"]
                 ;;[incanter "1.5.4"]
                 [gloss "0.2.2"]]

  ;; get clatrix from github
  :plugins [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :git-dependencies [["https://github.com/dcunited001/clatrix.git" "reshape"]]
  :source-paths ["src" ".lein-git-deps/clatrix/src/"]
  
  :jvm-opts ["-Xmx2g"]
;;  :aot [digits.draw.Frame]
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

;; incanter and awk images: http://pastebin.com/sYn517qh
;; - https://github.com/joeatwork/nma.simple/blob/master/src/nma/demo.clj

;; use sprites?
;; - http://www.coderanch.com/t/569886/GUI/java/efficient-spritesheet

;; JPanel w/ proxy
;; - http://stackoverflow.com/questions/1518933/image-processing-extending-jpanel-and-simulating-classes-in-clojure

;; use quil & processing for images?
;; - create-image to create an image
;; - then write pixels to it
;; - then write using set-image

;; double buffering in clojure (2009)
;; - http://www.curiousattemptbunny.com/2009/01/double-buffering-render-loop-in-clojure.html
;; - http://briancarper.net/blog/520/making-an-rpg-in-clojure-part-one-of-many

;; updating bufferedimages in swing using core.matrix data
;; - http://clojurefun.wordpress.com/2013/08/31/visualising-singapores-mobile-phone-data-with-core-matrix/
