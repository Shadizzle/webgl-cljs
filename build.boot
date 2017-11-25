(def project 'webgl-cljs)

(set-env! :source-paths #{"src" "resources"}
          :dependencies '[[org.clojure/clojure         "1.8.0"]
                          [org.clojure/clojurescript   "1.9.946"]
                          [pandeiro/boot-http          "0.8.3"]
                          [adzerk/boot-cljs            "2.1.4"]
                          [adzerk/boot-reload          "0.5.2"]
                          [adzerk/boot-cljs-repl       "0.3.3"]
                          [crisptrutski/boot-cljs-test "0.3.4"]
                          [com.cemerick/piggieback     "0.2.2"  :scope "test"]
                          [weasel                      "0.7.0"  :scope "test"]
                          [org.clojure/tools.nrepl     "0.2.13" :scope "test"]])

(require '[pandeiro.boot-http          :refer [serve]]
         '[adzerk.boot-cljs            :refer [cljs]]
         '[adzerk.boot-cljs-repl       :refer [cljs-repl]]
         '[adzerk.boot-reload          :refer [reload]])

(deftask dev []
  (comp
    (serve :dir "target")
    (watch)
    (reload)
    (cljs-repl)
    (cljs :source-map true :optimizations :none)))
