
(ns webdev.item.handler
  (:require [webdev.db :refer :all]
            [rethinkdb.query :as r]
            [webdev.item.view :refer [items-page]]
            [webdev.item.model :refer [create-item
                                       update-item
                                       read-items
                                       delete-item
                                       get-item]]))

(defn handle-create-item [req]
  (let [{:strs [name description]} (:params req)
        item-id (create-item {"name" name "description" description})]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}
    ))
(defn handle-index-items [req]
  (let [items (read-items)]
    {:status 200
     :headers {}}
     :body (items-page items))
  )

(defn handle-delete-item [req]
  (let [results (delete-item (get-in req [:params :id]))]
    (if (= 1 (:deleted results))
        {:status 302
         :headers {"Location" "/items"}
         :body ""}
        {:status 404
         :body "Item not found"
         :headers {}})))


(defn handle-update-item [req]
  (let [checked (get-in req [:params "checked"])
        results (update-item (get-in req [:params :id]) (not= "true" checked))]
       (if (= 1 (:replaced results))
         {:status 302
          :headers {"Location" "/items"}
          :body ""}
         {:status 404
          :body "Item not found"
          :headers {}}
         )
       )
  )

