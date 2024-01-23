(require 'clojure.string)

(def input_data (map clojure.string/split (clojure.string/split (slurp "input.txt") #"\n") [#" +" #" +"]))
(def input_data2 (map clojure.string/replace (clojure.string/split (slurp "input.txt") #"\n") [#" " #" +"] ["" ""]))

(def input_time2  (Double/parseDouble (first (rest (clojure.string/split (first input_data2) #":")))))
(def input_dist2  (Double/parseDouble (first (rest (clojure.string/split (first(rest input_data2)) #":")))))

(def input_times (into [] ( map #(Integer/parseInt %) (first(first(for [x input_data
      :let [y (vector (if
                       (= (first x) "Time:")
                        (rest x)
                        nil))]
      :when (not (nil? (first y)))]
  y ))))))
(def input_dists (into [] ( map #(Integer/parseInt %) (first(first(for [x input_data
      :let [y (vector (if
                       (= (first x) "Distance:")
                        (rest x)
                        nil))]
      :when (not (nil? (first y)))]
  y ))))))


(def demo_race_times [7 15 30])
(def demo_dists [9 40 200])
(def demo_race_time_dists (map vector demo_race_times demo_dists))

(defn calc_dist 
  ([charge_time race_time]
   (calc_dist charge_time race_time 1))
  ([charge_time race_time acc]
  
   (* 
    (* acc charge_time)
    (if (< race_time charge_time) 
       0 
       (- race_time charge_time))
  )))

; takes either a combined list of race-record / 
(defn winning_times 
  ([combineder]
   (winning_times (get combineder 0) (get combineder 1)))
  ([race_time dist_record]
  (for [times (range (+ race_time 1))
        :let [y (calc_dist times race_time)]
        :when (> y dist_record)]
    [y times])))

;; count_race_wins
; takes a vector of race times and record distances (equal length)
;returns a count of possible race wins as a result.
(defn count_race_wins [race_t race_rec]

  (for [x (map vector race_t race_rec)

        :let [z (count (winning_times x))]]
    z))
(calc_dist 1.0 7.0 1.0)


; PART 1 ANSWER!
(apply * (count_race_wins input_times input_dists))

; PART 2 ANSWERS
; (winning_times 71530 940200)
; also
;manually - eww (count (winning_times 71530 940200))

(count (winning_times input_time2 input_dist2))