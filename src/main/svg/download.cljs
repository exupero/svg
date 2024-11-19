(ns svg.download)

(defn blob [svg-content]
  (js/Blob. #js [svg-content] #js {:type "image/svg+xml"}))

(defn canvas-of-size [width height]
  (let [canvas (js/document.createElement "canvas")]
    (set! (.-width canvas) width)
    (set! (.-height canvas) height)
    (set! (.. canvas -style -width) (str width "px"))
    (set! (.. canvas -style -height) (str height "px"))
    canvas))

(defn canvas-blob [svg-blob {:keys [width height scale]}]
  (let [scale (* scale (or js/window.devicePixelRatio 1))
        canvas (canvas-of-size (* width scale) (* height scale))
        context (doto (.getContext canvas "2d")
                  (.setTransform scale 0 0 scale 0 0))
        img (js/Image.)
        url (js/URL.createObjectURL svg-blob)]
    (set! (.-src img) url)
    (js/Promise. (fn [res]
                   (set! (.-onload img)
                         (fn []
                           (.drawImage context img 0 0)
                           (js/URL.revokeObjectURL url)
                           (.toBlob canvas res)))))))

(defn download! [blob filename]
  (let [link (js/document.createElement "a")]
    (set! (.-download link) filename)
    (set! (.. link -style -display) "none")
    (js/document.body.appendChild link)
    (try
      (let [url (js/URL.createObjectURL blob)]
        (set! (.-href link) url)
        (set! (.-onclick link)
              (fn []
                (js/requestAnimationFrame #(js/URL.revokeObjectURL url))))
        (.click link))
      (catch :default e
        (js/console.error e))
      (finally
        (js/document.body.removeChild link)))))
