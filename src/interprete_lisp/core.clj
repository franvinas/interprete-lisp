(ns interprete-lisp.core
  (:gen-class)
  (:require [interprete-lisp.lisp :refer [repl]])
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (repl)
)
