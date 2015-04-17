(ns webdev.db
  (:require [rethinkdb.core :refer [connect close]]
            [rethinkdb.query :as r]))

(def conn (connect :host "127.0.0.1" :port 28015))

(defn run [cmd] (r/run cmd conn))

(defn ensure-db [name]
  (if-not (contains? (set (run (r/db-list))) name)
    (run (r/db-create name))))

(defn ensure-table [db name]
  (if-not (contains? (set (run (r/table-list db))) name)
    (run (r/table-create db name))))

(defn init [db-name]
  (ensure-db db-name)
  (def db (r/db db-name))
  (ensure-table db "items"))


