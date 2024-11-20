(ns svg.client)

(defn coord
  "Converts a clientX and clientY coordinate pair into a coordinate in the given SVG element."
  [^js/SVGSVGElement svg [x y]]
  (when svg
    (let [pt (.createSVGPoint svg)
          _ (set! (.-x pt) x)
          _ (set! (.-y pt) y)
          pt' (.matrixTransform pt (.inverse (.getScreenCTM svg)))]
      [(.-x pt') (.-y pt')])))
