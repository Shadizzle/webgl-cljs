(ns webgl-cljs.core)

(defn -main [& args]
  (let [root (js/document.getElementById "root")]
    (aset root "innerHTML" "<p>I'm dynamically created.</p>")))
