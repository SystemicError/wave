(ns wave.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:time 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:time (+ (:time state) 1)})

(defn draw-wave
  "Draw a wave"
  ([t] (draw-wave t (range (q/width))))
  ([t xs]
   (if (> (count xs) 2)
     (let [f #(if (> % t)
                0
                (* 100 (Math/sin (/ (- t %) 20))))
           x0 (first xs)
           x1 (first (rest xs))
           y0 (f x0)
           y1 (f x1)]
       (q/stroke 255 0 0 (- 255 (* 255 (/ x0 900))))
       (q/line x0 y0 x1 y1)
       (recur t (rest xs))))))

(defn draw-state [state]
  (let [t (:time state)]
    ; Clear the sketch by filling it with light-grey color.
    (q/background 255)

    (q/stroke-weight 4)

    (q/with-translation [0 (/ (q/height) 2)]
      (draw-wave t))

    ; save frame
    (q/save-frame "wave-####.png")
    ))


(q/defsketch wave
  :title "Wave"
  :size [900 210]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
