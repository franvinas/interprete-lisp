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

(deftest revisar-fnc-test
  (testing "Funcion revisar-fnc"
    (is (= (revisar-fnc '(*error* too-few-args)) '(*error* too-few-args)))
    (is (= (revisar-fnc '(too-few-args)) nil))
    (is (= (revisar-fnc '*error*) nil))
    (is (= (revisar-fnc nil) nil))
    (is (= (revisar-fnc ()) nil))
  )
)

(deftest revisar-lae-test
  (testing "Funcion revisar-lae"
    (is (= (revisar-lae '(1 2 3)) nil))
    (is (= (revisar-lae nil) nil))
    (is (= (revisar-lae ()) nil))
    (is (= (revisar-lae '(1 (*error* too-few-args) 3)) '(*error* too-few-args)))
    (is (= (revisar-lae '(1 (*error* too-few-args) (*error* too-many-args) 3)) '(*error* too-few-args)))
  )
)

(deftest actualizar-amb-test
  (testing "Funcion actualizar-amb"
    (is (= (actualizar-amb '(a 1 b 2 c 3) 'd 4) '(a 1 b 2 c 3 d 4)))
    (is (= (actualizar-amb '(a 1 b 2 c 3) 'b 4) '(a 1 b 4 c 3)))
    (is (= (actualizar-amb '(a 1 b 2 c 3) 'b (list '*error* 'mal 'hecho)) '(a 1 b 2 c 3)))
    (is (= (actualizar-amb () 'b 7) '(b 7)))
  )
)