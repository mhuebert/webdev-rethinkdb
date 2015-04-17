(ns webdev.item.view
 (:require [hiccup.page :refer [html5]]
           [hiccup.core :refer [html h]]))

(defn new-item []
  (html 
   [:form {:method "POST" :action "/items"}
    [:input {:name :name :placeholder "Name"}]
    [:br]
    [:input {:name :description :placeholder "Description"}]
    [:br]
    [:input {:type "submit"}]
    ]))

(defn items-page [items]
  (html5 {:lang :en}
         [:head
          [:title "Listronic"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:body
           [:div.container
            (if (seq items) 
              (for [i items]
                [:div {:style "margin-bottom:10px; border-bottom: 1px solid #eee; padding-bottom: 10px;"}
                  [:form {:action (str "/item/" (:id i)) :method "POST" :style "display:inline-block; float:left; margin-right: 10px;"}
                  [:input {:type "hidden" :name "_method" :value "PUT"}]
                  [:input {:type "hidden" :name "checked" :value (if (:checked i) "true" "false")}]
                  [:input {:type "submit" :style "width: 30px;height:30px;cursor:pointer;" :value (if (:checked i) "[✓]" "[ ]")}]
                   ]
                 [:strong (:name i)] [:br] 
                 (:description i)
                
                 [:form {:action (str "/item/" (:id i)) :method "POST"}
                  [:input {:type "hidden" :name "_method" :value "DELETE"}]
                  [:input {:type "submit" :style "color:red;margin:5px 0 0 38px; cursor:pointer;" :value "X"}]
                  ]
                 ]
                ) "No items.")
            ]
           (new-item)]]))

