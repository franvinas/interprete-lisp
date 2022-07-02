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

(deftest fnc-append-test
  (testing "Funcion fnc-append"
    (is (= (fnc-append '( (1 2) )) '(*error* too-few-args)))
    (is (= (fnc-append '( (1 2) (3) (4 5) (6 7) )) '(*error* too-many-args)))
    (is (= (fnc-append '( (1 2) 3 )) '(*error* list expected 3)))
    (is (= (fnc-append '( (1 2) A )) '(*error* list expected A)))
    (is (= (fnc-append '( (1 2) (3))) '(1 2 3)))
    (is (= (fnc-append '( (1 2) nil )) '(1 2)))
    (is (= (fnc-append '( () (1 2) )) '(1 2)))
    (is (= (fnc-append '(nil nil)) nil))
    (is (= (fnc-append '(() ())) nil))
  )
)

(deftest fnc-env-test
  (testing "Funcion fnc-env"
    (is (= (fnc-env () '(a 1 b 2) '(c 3 d 4)) '(a 1 b 2 c 3 d 4)))
    (is (= (fnc-env '(5) '(a 1 b 2) '(c 3 d 4)) '(*error* too-many-args)))
  )
)

(deftest fnc-equal-test
  (testing "Funcion fnc-equal"
    (is (= (fnc-equal '(1 1)) 't))
    (is (= (fnc-equal '(A a)) 't))
    (is (= (fnc-equal '("1" "1")) 't))
    (is (= (fnc-equal '(nil NIL)) 't))
    (is (= (fnc-equal '(1 2)) 'nil))
    (is (= (fnc-equal '(A B)) 'nil))
    (is (= (fnc-equal '("1" 1)) 'nil))
    (is (= (fnc-equal ()) '(*error* too-few-args)))
    (is (= (fnc-equal '(A)) '(*error* too-few-args)))
    (is (= (fnc-equal '(A a A)) '(*error* too-many-args)))
  )
)

(deftest fnc-add-test
  (testing "Funcion fnc-add"
    (is (= (fnc-add ()) '(*error* too-few-args)))
    (is (= (fnc-add '(3)) '(*error* too-few-args)))
    (is (= (fnc-add '(3 4))  '7))
    (is (= (fnc-add '(3 4 5)) '12))
    (is (= (fnc-add '(3 4 5 6)) '18))
    (is (= (fnc-add '(A 4 5 6)) '(*error* number-expected A)))
    (is (= (fnc-add '(3 A 5 6)) '(*error* number-expected A)))
    (is (= (fnc-add '(3 4 A 6)) '(*error* number-expected A)))
  )
)

(deftest fnc-sub-test
  (testing "Funcion fnc-sub"
    (is (= (fnc-sub ()) '(*error* too-few-args)))
    (is (= (fnc-sub '(3)) '-3))
    (is (= (fnc-sub '(3 4)) '-1))
    (is (= (fnc-sub '(3 4 5)) '-6))
    (is (= (fnc-sub '(3 4 5 6)) '-12))
    (is (= (fnc-sub '(A 4 5 6)) '(*error* number-expected A)))
    (is (= (fnc-sub '(3 A 5 6)) '(*error* number-expected A)))
    (is (= (fnc-sub '(3 4 A 6)) '(*error* number-expected A)))
  )
)

(deftest fnc-lt-test
  (testing "Funcion fnc-lt"
    (is (= (fnc-lt ()) '(*error* too-few-args)))
    (is (= (fnc-lt '(1)) '(*error* too-few-args)))
    (is (= (fnc-lt '(1 2)) 't))
    (is (= (fnc-lt '(1 1)) 'nil))
    (is (= (fnc-lt '(2 1)) 'nil))
    (is (= (fnc-lt '(A 1)) '(*error* number-expected A)))
    (is (= (fnc-lt '(1 A)) '(*error* number-expected A)))
    (is (= (fnc-lt '(1 2 3)) '(*error* too-many-args)))
  )
)
