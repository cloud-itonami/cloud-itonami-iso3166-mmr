(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "MMR" 0)
        s (registry/register-submit "eng-1" "MMR" 0)]
    (is (= "MMR-DFT-000000" (get d "draft_number")))
    (is (= "MMR-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "MMR" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

;; ------------------- day-ordinal arithmetic (flagship plumbing) -------------------

(deftest days-between-plain-month
  (testing "no leap-year involvement -- January has 31 days"
    (is (= 28 (registry/days-between "2026-01-01" "2026-01-29")))
    (is (= 31 (registry/days-between "2026-01-01" "2026-02-01")))))

(deftest days-between-leap-year
  (testing "2024 is a leap year (divisible by 4, not by 100) -- Feb has 29 days"
    (is (= 2 (registry/days-between "2024-02-28" "2024-03-01")))))

(deftest days-between-non-leap-century-year
  (testing "2100 would be divisible by 4 but is NOT a leap year (divisible by 100, not by 400)"
    (is (= 1 (registry/days-between "2100-02-28" "2100-03-01")))))

(deftest days-between-negative-when-reversed
  (is (= -28 (registry/days-between "2026-01-29" "2026-01-01"))))

;; ------------------- annual-return-status (FLAGSHIP) -------------------

(deftest annual-return-status-current-when-filed-on-or-before-due
  (testing "filed before the due date -> :current"
    (is (= :current (registry/annual-return-status
                      {:ar-due-date "2026-06-01" :ar-filed-date "2026-05-28"}))))
  (testing "filed exactly on the due date -> :current"
    (is (= :current (registry/annual-return-status
                      {:ar-due-date "2026-06-01" :ar-filed-date "2026-06-01"})))))

(deftest annual-return-status-late-fee-due-within-grace-window
  (testing "DICA's own 28-day grace window -- 9 days late -> :late-fee-due"
    (is (= :late-fee-due (registry/annual-return-status
                           {:ar-due-date "2026-06-01" :ar-filed-date "2026-06-10"}))))
  (testing "exactly 28 days late is still within the grace window -> :late-fee-due"
    (is (= :late-fee-due (registry/annual-return-status
                           {:ar-due-date "2026-01-01" :ar-filed-date "2026-01-29"})))))

(deftest annual-return-status-filed-after-suspension-beyond-grace-window
  (testing "29 days late is beyond DICA's own 28-day grace window -> :filed-after-suspension"
    (is (= :filed-after-suspension (registry/annual-return-status
                                     {:ar-due-date "2026-01-01" :ar-filed-date "2026-01-30"}))))
  (testing "243 days late -> :filed-after-suspension"
    (is (= :filed-after-suspension (registry/annual-return-status
                                     {:ar-due-date "2026-01-01" :ar-filed-date "2026-09-01"})))))

(deftest annual-return-status-gap-is-honestly-indeterminate
  (testing "missing either date is NOT modeled -- returns nil rather than guessing"
    (is (nil? (registry/annual-return-status {:ar-due-date "2026-06-01"})))
    (is (nil? (registry/annual-return-status {:ar-filed-date "2026-06-01"})))
    (is (nil? (registry/annual-return-status {})))))

(deftest annual-return-status-mismatch-is-entity-scope-gated
  (testing "an engagement with no claimed status at all is never flagged"
    (is (false? (registry/annual-return-status-mismatch?
                 {:ar-due-date "2026-01-01" :ar-filed-date "2026-09-01"}))))
  (testing "a claimed status that does NOT match the independently recomputed status -> mismatch"
    (is (true? (registry/annual-return-status-mismatch?
                {:ar-due-date "2026-01-01" :ar-filed-date "2026-09-01"
                 :claimed-ar-status :current}))))
  (testing "a claimed status that DOES match -> not flagged"
    (is (false? (registry/annual-return-status-mismatch?
                 {:ar-due-date "2026-06-01" :ar-filed-date "2026-05-28"
                  :claimed-ar-status :current})))
    (is (false? (registry/annual-return-status-mismatch?
                 {:ar-due-date "2026-06-01" :ar-filed-date "2026-06-10"
                  :claimed-ar-status :late-fee-due}))))
  (testing "an indeterminate recompute (missing dates) is never treated as a mismatch, even if a claim is present"
    (is (false? (registry/annual-return-status-mismatch?
                 {:claimed-ar-status :current})))))
