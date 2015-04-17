(ns webdev.core
  (:require [webdev.item.model :as items]
            [webdev.item.handler :as handler]
            [webdev.db :as db]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :as route]
            [ring.handler.dump :refer [handle-dump]] ))

(defn yo [req]
  (str "Yo, " (:name (:route-params req))))

(defn calc [req]
  (let [{:keys [a op b]} (:route-params req)
        a (Integer. a)
        b (Integer. b)
        op (cond (= op "+") +
                 (= op "-") -
                 (= op "*") *
                 (= op ":") /)]
    (str (op a b))))

(defroutes routes

   ; baby steps
   (GET "/" [] "Hello, World.")
   (ANY "/request" [] handle-dump)
   (GET "/yo/:name" [] yo)
   (GET "/calc/:a/:op/:b" [] calc)
   (GET "/goodbye" [] "Goodbye, Cruel World!")
   
   ; REST
   (GET "/items" []  handler/handle-index-items)
   (POST "/items" [] handler/handle-create-item)
   (DELETE "/item/:id" [] handler/handle-delete-item)
   (GET "/about" [] "I'm Matt, and I'm building this to learn out how to develop apps in Clojure.")
   (PUT "/item/:id" [] handler/handle-update-item)
   (route/not-found "Not found"))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "My first server")))

(def sim-methods {"PUT" :put
                  "DELETE" :delete})

(defn wrap-simulated-methods [hdlr]
  (fn [req]
    (if-let [method (and (= :post (:request-method req))
                         (sim-methods (get-in req [:params "_method"])))]
      (hdlr (assoc req :request-method method))
      (hdlr req))))

(def app
  (-> routes
      wrap-simulated-methods
      wrap-params
      wrap-file-info
      (wrap-resource "static")
      wrap-server))

(defn -dev-main [port]
  (db/init "lispcast_webdev_dev")
  (jetty/run-jetty (wrap-reload #'app) {:port (Integer. port)}))

(defn -main [port]
  (db/init "lispcast_webdev")
  (jetty/run-jetty app {:port (Integer. port)}))
