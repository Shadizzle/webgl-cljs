(ns webgl-cljs.core)

(def vertices [-0.5  0.5  0.0
               -0.5 -0.5  0.0
                0.5 -0.5  0.0])

(def indices [0, 1, 2])

(def vert-code
  (str
   "attribute vec3 coordinates;"
   "void main(void) {"
   " gl_Position = vec4(coordinates, 1.0);"
   "}"))

(def frag-code
  (str
   "void main(void) {"
   " gl_FragColor = vec4(0.0, 0.0, 0.0, 0.1);"
   "}"))

(defn -main [& args]
  (let [root (js/document.getElementById "root")
        gl   (.getContext root "webgl")
        vertex-buffer (.createBuffer gl)
        index-buffer  (.createBuffer gl)
        vert-shader   (.createShader gl (.-VERTEX_SHADER gl))
        frag-shader   (.createShader gl (.-FRAGMENT_SHADER gl))
        shader-program (.createProgram gl)]
    (doto gl
      ;; Bind, write vertices to ARRAY_BUFFER, unbind
      (.bindBuffer (.-ARRAY_BUFFER gl) vertex-buffer)
      (.bufferData (.-ARRAY_BUFFER gl)
                   (js/Float32Array. vertices)
                   (.-STATIC_DRAW gl))
      (.bindBuffer (.-ARRAY_BUFFER gl) nil)
      ;; Bind, write indices to ELEMENT_ARRAY_BUFFER, unbind
      (.bindBuffer (.-ELEMENT_ARRAY_BUFFER gl) index-buffer)
      (.bufferData (.-ELEMENT_ARRAY_BUFFER gl)
                   (js/Uint16Array. indices)
                   (.-STATIC_DRAW gl))
      (.bindBuffer (.-ELEMENT_ARRAY_BUFFER gl) nil)
      ;; Assign vertex code to shader and compile
      (.shaderSource vert-shader vert-code)
      (.compileShader vert-shader)
      ;; Assign fragment code to shader and compile
      (.shaderSource frag-shader frag-code)
      (.compileShader frag-shader)
      ;; Attach both shaders, link and use program
      (.attachShader shader-program vert-shader)
      (.attachShader shader-program frag-shader)
      (.linkProgram shader-program)
      (.useProgram shader-program)
      ;; Bind both buffers
      (.bindBuffer (.-ARRAY_BUFFER gl) vertex-buffer)
      (.bindBuffer (.-ELEMENT_ARRAY_BUFFER gl) index-buffer))
    ;; ???
    (let [coord (.getAttribLocation gl shader-program "coordinates")]
      (doto gl
        (.vertexAttribPointer coord 3 (.-FLOAT gl) false 0 0)
        (.enableVertexAttribArray coord)))
    (doto gl
      ;; Clear canvas
      (.clearColor 0.5 0.5 0.5 0.9)
      ;; Enable depth test?
      (.enable (.-DEPTH_TEST gl))
      ;; Clear color buffer bit?
      (.clear (.-COLOR_BUFFER_BIT gl))
      ;; Set viewport
      (.viewport 0 0 (.-width root) (.-height root))
      ;; Draw triangle
      (.drawElements (.-TRIANGLES gl)
                     (.-length indices)
                     (.-UNSIGNED_SHORT gl)
                     0))))
