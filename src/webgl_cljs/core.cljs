(ns webgl-cljs.core)

(defn- fClear []
  (this-as this
    (.clear this
      (bit-or (.-COLOR_BUFFER_BIT this)
              (.-DEPTH_BUFFER_BIT this)))
    this))

(defn- fSetSize [w, h]
  (this-as this
    (set! (.. this -canvas -style -width) (str w "px"))
    (set! (.. this -canvas -style -height) (str h "px"))
    (set! (.. this -canvas -width) w)
    (set! (.. this -canvas -height) h)
    (.viewport this 0 0 w h)
    this))

(defn init-gl-context [canvas]
  (doto (.getContext canvas "webgl2")
    (.clearColor 1.0 1.0 1.0 1.0)
    (aset "fClear" fClear)
    (aset "fSetSize" fSetSize)))

(defn -main [& args]
  (let [root (js/document.getElementById "root")
        gl (init-gl-context root)]
    (doto gl
      (.fSetSize 500 500)
      (.clear))))
