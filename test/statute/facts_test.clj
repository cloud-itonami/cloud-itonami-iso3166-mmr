(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest mmr-has-spec-basis
  (let [sb (facts/spec-basis "MMR")]
    (is (= 3 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["MMR" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= #{"mmr.companies-law-2017" "mmr.companies-regulations-2018"}
         (set (mapv :statute/id (facts/by-topic "MMR" :incorporation)))))
  (is (= #{"mmr.investment-law"}
         (set (mapv :statute/id (facts/by-topic "MMR" :foreign-investment)))))
  (is (empty? (facts/by-topic "ATL" :foreign-investment))))

(deftest mmr-labour-law-is-honestly-absent
  (is (empty? (facts/by-topic "MMR" :labor))
      "this iteration could not independently confirm a Myanmar labour-law citation with a law number/date -- deliberately not claimed, see namespace docstring"))

(deftest mmr-tax-law-is-honestly-absent
  (is (empty? (facts/by-topic "MMR" :tax))
      "this iteration only found tax-law CATEGORY NAMES (no law numbers/dates) from a single unofficial secondary source -- deliberately not claimed, see namespace docstring"))
