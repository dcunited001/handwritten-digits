(ns digits.ui
  (:use [seesaw.core])
  (:require [seesaw.graphics :as graphics]
            [seesaw.bind :as b]
            [seesaw.color :as color]))
(def refresh-rate 100)

(defn add-behaviors [root] 
  (let [state-atom (atom 1)
        canvas (select root [:#canvas])
        timer (timer (fn [_] (swap! state-atom + 1))
                     :delay 100)]
    
    (listen root :window-closed (fn [_] (.stop timer)))

    (config! canvas :focusable? true)
    (.requestFocusInWindow canvas)

    (b/bind state-atom
            (b/b-do [state] (repaint! canvas)))

    root
  )
)

(defn make-canvas [paint-fn]
  (canvas :id :canvas :background :black :paint paint-fn))

(defn make-frame [sizex sizey canvas]
  (frame
   :title      "Digits"
   :on-close   :dispose
   :resizable? false
   :size       [(* 2 sizex) :by (* 2 sizey)]
   :content    canvas)
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

