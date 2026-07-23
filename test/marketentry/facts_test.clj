(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest mmr-has-spec-basis
  (let [sb (facts/spec-basis "MMR")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/annual-return-spec-basis "MMR")))
    (is (some? (facts/mic-permit-spec-basis "MMR")))))

(deftest mmr-rep-spec-basis-is-honestly-absent
  (testing "no verifiable Myanmar representative-exclusion-extension provision was located -- deliberately not claimed"
    (is (nil? (facts/rep-spec-basis "MMR")))))

(deftest mmr-corporate-number-spec-basis-is-honestly-absent
  (testing "whether Myanmar's company registration number bundles a TIN (like LAO's Enterprise Registration Certificate) was not independently confirmed -- deliberately not claimed"
    (is (nil? (facts/corporate-number-spec-basis "MMR")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "MMR")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "MMR" all)))
    (is (not (facts/required-evidence-satisfied? "MMR" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["MMR" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest annual-return-spec-basis-criteria
  (let [ar (facts/annual-return-spec-basis "MMR")]
    (is (= 28 (get-in ar [:annual-return-criteria :late-fee-grace-period-days])))
    (is (= 100000 (get-in ar [:annual-return-criteria :late-fee-mmk])))
    (is (= 50000 (get-in ar [:annual-return-criteria :registration-fee-mmk])))
    (is (= 6 (get-in ar [:annual-return-criteria :suspension-to-strikeoff-months])))))

(deftest mic-permit-spec-basis-has-no-fabricated-article-number
  (testing "DICA's own MIC-permit page gives no article/section number -- this catalog does not invent one"
    (let [mp (facts/mic-permit-spec-basis "MMR")]
      (is (some? mp))
      (is (string? (:mic-permit-legal-basis mp))))))
