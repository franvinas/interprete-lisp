(ns interprete-lisp.core-test
  (:require [clojure.test :refer :all]
            [interprete-lisp.core :refer :all]
            [interprete-lisp.lisp :refer :all]))

(deftest igual-test
  (testing "Funcion igual"
    (is (true? (igual? 1 1)))
    (is (false? (igual? 1 2)))
    (is (true? (igual? 'a 'a)))
    (is (true? (igual? 'A 'A)))
    (is (true? (igual? 'a 'A)))
    (is (true? (igual? 'A 'a)))
    (is (false? (igual? 'a 'b)))
    (is (true? (igual? '(a b c) '(A B C))))
    (is (false? (igual? '(a b c) '(A B D))))
    (is (true? (igual? nil nil)))
    (is (true? (igual? nil 'NIL)))
    (is (true? (igual? 'NIL nil)))
    (is (true? (igual? 'NIL 'NIL)))
    (is (true? (igual? nil ())))
    (is (true? (igual? 'NIL ())))
    (is (true? (igual? () ())))
    (is (false? (igual? () '(nil))))
    (is (true? (igual? "a" "a")))
    (is (false? (igual? "a" "A")))
    (is (false? (igual? 'a "a")))
    (is (false? (igual? 'a "A")))
  )  
)

(deftest error-test
  (testing "Funcion error"
    (is (true? (error? '(*error* too-few-args))))
    (is (true? (error? (list '*error* 'too-few-args))))
    (is (true? (error? (list '*ERROR* 'too-few-args))))
    (is (true? (error? (list '*Error* 'too-few-args))))
    (is (true? (error? (list '*error*))))
    (is (false? (error? (list 'too-few-args))))
    (is (false? (error? '*error*)))
    (is (false? (error? ())))
    (is (false? (error? nil)))
  )
)

;; (deftest revisar-fnc-test
;;   (testing "Funcion revisar-fnc"
;;     (is ((= '(*error* too-few-args) (revisar-fnc '(*error* too-few-args)))))
;;     ;; (is ((= nil (revisar-fnc '(too-few-args)))))
;;     ;; (is ((= nil (revisar-fnc '*error*))))
;;     ;; (is ((= nil (revisar-fnc nil))))
;;     ;; (is ((= nil (revisar-fnc ()))))
;;   )
;; )
