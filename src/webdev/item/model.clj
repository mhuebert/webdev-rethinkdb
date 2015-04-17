(ns webdev.item.model
 (:require [webdev.db :refer [db run conn]]
           [rethinkdb.query :as r]) )

(defn create-item [item]
  (-> db
      (r/table "items")
      (r/insert [item])
      (r/run conn)
      (:generated_keys)
      (first)))

(defn update-item [id checked]
  (-> db
      (r/table "items")
      (r/get id)
      (r/update {:checked checked})
      (r/run conn)))

(defn read-items []
  (-> db
      (r/table "items")
      (r/run conn)))

(defn delete-item [id]
  (-> db
      (r/table "items")
      (r/get id)
      (r/delete)
      (r/run conn)))

(defn get-item [id]
  (-> db
      (r/table "items")
      (r/get id)
      (r/run conn)))
