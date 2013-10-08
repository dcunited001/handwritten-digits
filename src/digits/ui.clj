(ns digits.ui
  (:use [seesaw.core])
  (:require [seesaw.graphics :as graphics]
            [seesaw.bind :as b]
            [seesaw.color :as color]))

(defn add-behaviors [root] 
  (let [canvas (select root [:#canvas])]
    ;;(listen root :window-closed (fn [_] (.stop timer)))
    (config! canvas
             ;; :paint (fn [c g] (paint c g))
             :focusable? true)
    (.requestFocusInWindow canvas)
    root
  )
)

(defn make-canvas [paint-fn]
  (canvas :id :canvas :background :black :paint paint-fn))

(defn make-frame [sizex sizey & canvas]
  (-> (frame
         :title      "Digits"
         :on-close   :dispose
         :resizable? false
         :size       [sizex :by sizey]
         :content    canvas)
      )
)

(defn show-frame [f]
  (show! f))

;;https://github.com/mikera/singa-viz/blob/develop/src/main/clojure/mikera/singaviz/main.clj

;; (defn component 
;;   "Creates a component as appropriate to visualise an object x" 
;;   (^JComponent [x]
;;     (cond 
;;       (instance? BufferedImage x) (JIcon. ^BufferedImage x)
;;       :else (error "Don't know how to visualize: " x))))

