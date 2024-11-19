(ns svg.hiccup)

(defn svg [attrs & children]
  (into [:svg (assoc attrs :xmlns "http://www.w3.org/2000/svg")]
        children))
