(defproject webdev "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.3.2"]
                 [compojure "1.3.3"]
                 [rethinkdb "0.4.39"]
                 [hiccup "1.0.5"]]
  :main webdev.core
  :plugins [[cider/cider-nrepl "0.9.0-SNAPSHOT"]]
  :profiles {:dev
          {:main webdev.core/-dev-main}}
  )

