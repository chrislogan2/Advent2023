42 ; an integer
; this is comment 
-1.5 ; floating point
22/7 ; is a ratio :)
; https://clojure.org/guides/learn/functions
;character
"hello"
\e ; a single char
#"[0-9]+" ; a regular expressions

map ; a sumbol
+ ; another symbol (most punctuation is allowed? )
clojure.core/+ ; a namespaced symbol
nil ; clj null val
true false ; booleans
:alpha ; an keyword
:release/alpha ; a keyword with a namespace

;symbols are used to refer to other stuff like functions, values, namespaces, etc
;symbols can optionally have their own namespace.
; 3 special symbols are read as diff types, nil, true, false.
; keywors with leading colon always eval to their self, frequently used as enum or attribute names.

;collections
'(1 2 3) ; a lis
[1 2 3] ; a vector
#{1 2 3} ; a set
{:a 1, :b 2} ; a map

; *1 lat result from repl
; *2 last 2 result from repl... etc
(+ 3 4) ; 7
(+ 3 *1) ; 10
(+ *1 *2); 17
;load a library
(require '[clojure.repl :refer :all])

(doc +) ; show docs for + function
(apropos "+")
; to find a ffunc that matches a string or regex pattern
(find-doc "trim")
; find the docs?!?!
(dir clojure.repl)
; show all things in a namespace.
(source dir)
; see source for a function :)

; def -> save a piece of data for later
(def x 7) ; x is 7
(+ x x) ; x is 7, so 14
;string printing (print/println for HUMANS)
; (they return nil but print)
; readable as data (prn / pr)
(println "Jarring isn't it")
(println "Jarring isn't it? " (+ x 2))
(prn "a test charcters composition: " (* 2 x))
(println *1)

; exercises
(+ 7654 1234)
(/ (+ 7 (* 3 4) 5) 10)
(doc mod)
(doc rem)
(find-doc "trace") ; to find pst 


; functions!
(defn greet [name] (str "Hello, " name))
(greet "Chris")

;multiple # params same func:
(defn messenger
  ([] (messenger "Hello World")) ; with no args
  ([msg] (println msg))) ; with 1 args
(messenger)
(messenger "Hey There!")

;variadic function / takes a variable number of params
; must occur at end of param list
;beginning marked with &
(defn hello [greeting & who]
  (println greeting who ", that is " (count who) " people"))
(hello "hey boys: " "chris" "john" "jeff" "jaquelina")

;an anonymous function, created with fn
(fn [message] (println message))
; defn is really equivalent to an anonymous function followed 
; by a def to bind to a name
(def demo_func (fn [message] (println message)))
(defn demo_func [message] (println message))

;shorter form for anon function syntax by clojure reader
; #()
; #(+ 6 %) -> (fn [x] (+ 6 x))
; #(+ %1 %2) -> (fn [x y] ( + x y))
; #(println %1 %2 %&) equivalent to (fn [x y & zs] (println(x y zs))
;#(vector % )

; apply function takes arguemnts from a sequence
(apply f '(1 2 3 4)) ;;  same as (f 1 2 3 4) !!
(apply f 1 2 '(3 4)) ;; same as ^^
; demo:
(defn plot [shape coords]
  (plotxy shape (first coords) (second coords)))
(defn plot [shape coords]
  (apply plotxy shape coords))
;locals, closures
;let "lexical scope" for locals
(let [name "value"
      name2 13] (println "this code uses the local with " name name2))

(defn messenger [msg]
  (let [a 7
        b 5
        c (clojure.string/capitalize msg)]
    (println a b c)) ;; end of the scope of let
) ;; end of messenger function
(messenger "hey fuckers")

;closures: fn special form creates a closure,
; it closes the surrounding lexical scope and captures their
; valures beyond scope.

;eg:
(defn messenger-builder [greeting]
  (fn [who] (println greeting who))) ;; closes over greeting
;; greeting provided here then goes out of scope?

;; messenger builder returns anonymous function containing greeting! with param who
;; therefore we can change the "who" ( see below vvv)
(def hello-er (messenger-builder "hello"))
(def fuck-off (messenger-builder "fuck you, "))
;; greeting value still avail bc hello-er is a closure
(hello-er "world!")

(fuck-off "Jared!")

;; java interop  v v v 

; instantiate a object
;new Widget("foo")
; (Widget."foo")

; instance method
;rnd.nextInt()
;(.nextInt rnd)
;
;instance fields
;object.field
;(.-field object)

; static methods
; Math.sqrt(25.0)
; (Math/sqrt 25.0)

; static field
; Math.PI
; Math/PI

;Java methods are not functions,
; can't store them or pass as args
; can wrap in funtions if req'd 

;;function to action .length on an argument
(def lentest (fn [obj] (.length obj)))

#(.length %)
(lentest (String. "This string is long"))
; same thing.

(defn greet [] (println "Hello"))
(greet)
(def greet (fn [] (println "Hello")))
(greet)
(def greet #(println "Hello"))
(greet)

(doc str)

(defn greeting
  ([] (greeting "World"))
  ([x] (greeting "Hello" x))
  ([x y] (str x ", " y "!")))

(greeting)
(greeting "1 arg")
(greeting "not a" " great greeting")

;; For testing
(assert (= "Hello, World!" (greeting)))
(assert (= "Hello, Clojure!" (greeting "Clojure")))
(assert (= "Good morning, Clojure!" (greeting "Good morning" "Clojure")))


(defn do-nothing [x] x)
(do-nothing "15")

(defn always-same
  ([& x] 100))
(always-same 200)

(defn make-thingy [x] (fn ([] x)
                        ([& y] x)))
(def xyzx (make-thingy 14.76))

(let [n (rand-int Integer/MAX_VALUE)
      f (make-thingy n)]
  (assert (= n (f)))
  (assert (= n (f 123)))
  (assert (= n (apply f 123 (range)))))

(defn triplicate [f]
  (f) (f) (f))
(triplicate greeting)

(defn opposite [f]
  (fn [& args] (not (apply f args))))

(defn triplicate2 [f & args]
  (triplicate #(apply f args)))

(triplicate2 println "poop" "farts")

(assert (= -1.0 (Math/cos Math/PI)))
(assert (= 1.0 (+ (Math/pow (Math/sin 25.0) 2)
                  (Math/pow (Math/cos 25.0) 2))))
; sin(x)^2 + cos(x)^2 = 1.0

(def uri (java.net.URL. "https://www.w3.org"))
(def reader (slurp (.openStream uri)))
(str reader)

(defn http-get [url]
  (slurp
   (.openStream
    (java.net.URL. url))))

(http-get "https://www.w3.org")
(assert (.contains (http-get "https://www.w3.org") "html"))

(defn http-get [url]
  (slurp url))
(http-get "https://www.w3.org")
(assert (.contains (http-get "https://www.w3.org") "html"))


(defn one-less-arg [f x] (fn [& args] (apply f x args)))

(defn two-fns [f g]
  (fn [arg]
    (f (g arg))))
(def twofn (two-fns * +))
(twofn 12)

; sequential collections :)
; vector, list, map, set
; vector/list are ordered
[1 2 3] ; vecotr
;index access
(def vec1 ["abc" false 99])
(get vec1 0)
(get vec1 1)
(get vec1 30) ; will return nil
(count vec1) ; 3

;can calso construct vecs like this:
(vector 1 2 3 56)
; can add elements with conj function CONJOIN no conjugate lol
(conj vec1 4 5 "this")

;immutable
(vec1) ; still just "abc" false 99

; Lisps (lists)
(def cards '(10 :ace :jack 9))
; lists arent indexed so they must be walked (first and rest)
(first cards)
(rest cards)

;add elements the same way, adds at start rather than the tail thoguh
(conj cards :queen)
(first cards)

;lists can be used as a astack with peek and pop
(def stack '(:a :b))

(peek stack)
(pop stack)
; obviously add with conj

; hashed collections :)
; sets, maps

;Sets
(def players #{"Alice","Bob", "Kelly"})
; add to a set with conj
(conj players "Chris")
;remove from a set 1 or more with disj (disjoin)
(disj players "Alice" "Bob" "Sally")
; you can disjoin things that aren't in a set, nbd.
; contains?
(contains? players "Kelly")

;sorting sets.
(conj (sorted-set) "Bravo" "Charlie" "Sigma" "Alpha")
;sorted-set-by uses a custom comparator instead of "natural" order by type

;into -> put 1 collection into another
(def new-players ["Tim" "Sue" "Greg"])
(into players new-players)

; maps
; are key-> value maps surrounded by {}
(def scores {"Fred" 1400
             "Bob" 1240
             "Angela" 1024})
(def scores {"Fred" 1400, "Bob" 1240, "Angela" 1024})
; same as prev
; note commas are treated as whitespace so you can use them to
; separate things where possible.

;adding vals with "assoc" (associate)
(assoc scores "Sally" 0)
; (assoc,scores,"Sally",0)
;if a key alreay exists, its value is replaced
(assoc scores "Bob" 0)

;removing key-value pairss
;dissoc (dissociate)
(dissoc scores "Bob")

;lookup by key
(get scores "Angela")

; can use them like a function kinda
; as a constant lookup table
; / enum?
(def directions {:north 0, :east 1, :south 2, :west 3})

(directions :north)

;don't use a map lieke this unless you can guarantee it wont be nil
(def bad-lookup-map nil)
(bad-lookup-map :foo)

;lookup with default -- can specify the default if key not found as a param
(get scores "Sam" 0)
(directions :northwest -1)
; helps distinguish between a missing key and a key with nil value.

(contains? scores "Fred")
(find scores "Fred")
; get keys OR vals
(keys scores)
(vals scores)

; map building
; zipmap can "zip" 2 sequences together to a map
(def players #{"Alice" "Bob" "Kelly"})

(zipmap players (repeat 0))

(into {} (map (fn [player] [player 0]) players))

;; with reduce
(reduce (fn [m player]
          (assoc m player 0)) {} ; init value
        players)

;;combining maps 
(def new-scores {"Angela" 300 "Jeff" 900})

(merge scores new-scores)
; by default if same key, rightmost one wins
; or can use a custom function to determine
(merge-with + scores new-scores)
; the function is called with both values to get new value

;; sorted maps
; similar to sorted sets 

(def sm (sorted-map
         "Bravo" 204
         "Alfa" 35
         "Sigma" 99
         "Charlie" 100))
;; representing app domain info using same set of fields 
;; known in advance u can use keyword keys
(def person {:first-name "Kelly"
             :last-name "Keen"
             :age 32
             :occupation "Programmer"})

;Field accessors, we can acess looking up key by value
(get person :occupation)
(person :occupation)

;"apparently" the most common way is to invoke the key word
; when a keyword is invoked, it looks itself up in the associative data strct it was passed
(:occupation person)

;keyword invocatoin takes optional def value
(:favorite-coloor person "Beige")

;update a field
(assoc person :occupation "Baker")
;dissoc to remove a field altogether
(dissoc person :age)

;nesting entities
(def company
  {:name "Widgets r us"
   :address {:street "123 Main St"
             :city "Springfield"
             :state "IL"}})
; get-in to acecss fields at any level in nested entities
(get-in company [:address :city])

;assoc-in or update-in to modify nested entries
(assoc-in company [:address :street] "303 Broadway")

;Records
; an alternative to using maps for people bassed things
; generally better performance
; have named type that can be used for polymorphism
(defrecord Person [first-name last-name age occupation])
;positional constructor
(def kelly (->Person "Kelly" "Keen" 32 "Programmer"))
;map constructor - generated
(def kelly (map->Person {:first-name "Kelly"
                         :last-name "Keen"
                         :age 32
                         :occupation "Programmer"}))
;records can be invoked same as maps, but cant be used 
; asa function like maps.
(:occupation kelly)

;FLOW CONTROL

;if
; (if (CONDITION) THEN ELSE)
(str "2 is " (if (even? 2) "even" "odd"))
; all values are true implicitly except false and nil
(if true :truthy :falsey)
(if (Object.) :truthy :falsey)
(if [] :truthy :falsey)
(if 0 :truthy :falsey) ; empty collections & zeros r true
(if false :truthy :falsey)
(if nil :truthy :falsey)

;if only takes a single expression
; do creates larger blocks
; only do this if ur bodies have side effects
(if (even? 5)
  (do (println "Even")
      true)
  (do (println "Odd!")
      false))
;when is if witht only a then branch (no else)
; it can eval any number of statements as the body, no "do" req'd
(when (neg? x)
  (throw (RuntimeException. (str "x must be positive: " x))))

;cond is a series of tests and expressions,
; each test if eval in order, and expression evals,
; and returns for first true test
; switch case kinda
(let [x 5]
  (cond
    (< x 2) "X is less thjan 2"
    (< x 10) "x is less than 10"))
; a common idiom to add an "else" or default case is to use a keyword like :else at the end
(let [x 11]
  (cond
    (< x 2) "x is less than 2"
    (< x 10) "x is less than 10"
    :else "x is greater than or equal to 10"))

; case -> literally case
; compares arg to a series of values to find a match
; done in constant time. 
; but each value must be a literal (compile time)
(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"))
(foo 10)
(foo 11)
; case iwth else
; can have a trailing expression if none matches
(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"
    "x is neither 5 nor 10"))
(foo 11)

;iteration for side effects
; dotimes eval expression N times
; returns nil
(dotimes [i 3]
  (println i))

;doseq 
; iterate over a sequqnce
; if lazy sequence, force evaluation
; reurns nil
(doseq [n (range 3)]
  (println n))

; with multiple bindings
; liked nested foreach loops
; processes all permutations :)
(doseq [letter [:a :b]
        number (range 3)] ; list of 0, 1, 2
  (prn [letter number]))

; for - list comprehension - not a for loop!
;generator function for seq permutation :o
; bindings behave like doseq
(for [letter [:a :b]
      number (range 3)]
  [letter number])
; returns a list of pairs?
;

;recursion / iteration
;recur & sequence abstraction
; recur is classic iteration.
; consumers don't control it, a lower level facility
;sequences represent iteration as values
; reducerts represent iteration a sfunction composition

;loop / recur
; loop defines the bindings, recur re-executes loop w new 
; bindings 
(loop [i 0]
  (if (< i 10)
    (recur (inc i))
    i))
; defn and recur
; function args are implicit loop bindings!
(defn increase [i]
  (if (< i 10)
    (recur (inc i))
    i))
(increase 9)

;exceptions!
;try catch finally a la java
(try
  (/ 2 1)
  (catch ArithmeticException e "divide by zero")
  (finally
    (println "cleanup")))

; throw exception
(try
  (throw (Exception. "something went wrong"))
  (catch Exception e (.getMessage e)))

;exceptions with clojure data
;ex-info takes message / map
; ex-data gets map back out
;   or nil if not created with ex-info
(try
  (throw (ex-info "There was an problem" {:detail 43}))
  (catch Exception e 
    (prn (:detail (ex-data e)))))

; with-open
(let [f (clojure.java.io/writer "/tmp/new")]
  (try
    (.write f "some text")
    (finally
(.close f))))

(with-open [f (clojure.java.io/writer "/tmp/new")]
  (.write f "some text"))