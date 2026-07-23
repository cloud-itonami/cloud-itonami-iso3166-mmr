(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-sector market-entry filing --
  every jurisdiction assigns its own format. This namespace does NOT
  invent one; it builds a jurisdiction-scoped sequence number and
  validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `annual-return-status` / `annual-return-status-mismatch?` are THIS
  vertical's own new ground-truth recompute, grounding MMR's flagship
  governor check (`marketentry.governor/annual-return-status-mismatch-
  violations`): DICA's own live 'Annual Return' guidance page
  (dica.gov.mm/en/annual-return, own text, fetched 2026-07-23 -- see
  `marketentry.facts` for the full epistemic caveat on this page's
  Burmese-language content and Burmese-numeral-glyph figures) describes
  a staged consequence chain for a company's own declared AR due date
  vs. its own declared AR filed date: filed on or before the due date is
  fine; filed within 28 days after the due date incurs a 100,000 MMK
  late fee; filed (or still unfiled) beyond that 28-day grace window
  means the company would already have been marked 'Suspended' by DICA
  before it could file at all. `day-ordinal` is a small pure
  proleptic-Gregorian day-count -- NOT a full calendar library, no host
  date API (`java.time`/`js/Date`), so the recompute is byte-identical on
  every `.cljc` target (JVM/CLJS/nbb/wasm) -- the same portability
  discipline `foreign-company-registration-late?`
  (cloud-itonami-iso3166-brn sibling) established for pure string-
  `compare` date ORDERING; this namespace needs an actual day COUNT (not
  just ordering, since DICA's own rule has a 28-day THRESHOLD, not a
  bare before/after test), so it earns its own small ordinal function
  instead of reusing a plain `compare`.

  This is a DIFFERENT check SHAPE from every prior sibling this
  namespace's author has read (idn/lao/brn): not an authority-
  jurisdiction routing classification between two legislative bodies
  (LAO), not a bare precedence test between two dates (BRN), not a
  boolean field-presence read (IDN) -- it is a THREE-TIER staged
  deadline/grace-window classification recomputed from a single day-count
  delta, grounded in a real (if administrative-guidance-level, not
  article-numbered) DICA rule that this iteration independently
  corroborated is CURRENTLY, ACTIVELY enforced via DICA's own real, dated
  struck-off notifications (see `marketentry.facts`).

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real government system. It builds the RECORD an operator
  would keep, not the act of submitting a MyCO Annual Return or MIC
  filing itself (that is `marketentry.operation`'s `:filing/submit`,
  always human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

;; ------------------- pure day-ordinal arithmetic (flagship) -------------------

(defn- parse-nonneg-int
  "A hand-rolled non-negative decimal integer parser (\"28\" -> 28,
  \"2026\" -> 2026) -- avoids `Long/parseLong`/`js/parseInt` so this
  namespace stays a single portable .cljc body with zero platform
  branching, consistent with the pure-arithmetic, no-host-API discipline
  `foreign-company-registration-late?` (BRN sibling) already established
  for date handling in this vertical family."
  [s]
  (reduce (fn [acc ch] (+ (* acc 10) (- (int ch) (int \0)))) 0 s))

(defn- leap-year? [y]
  (and (zero? (mod y 4)) (or (not (zero? (mod y 100))) (zero? (mod y 400)))))

(def ^:private cumulative-days-before-month
  "Days elapsed before the 1st of month m (index 0 = January) in a
  NON-leap year."
  [0 31 59 90 120 151 181 212 243 273 304 334])

(defn- day-ordinal
  "Pure proleptic-Gregorian day ordinal for a plain ISO-8601
  \"YYYY-MM-DD\" string (ordinal 0 = 0001-01-01) -- just enough calendar
  arithmetic to subtract two dates and get a day COUNT, not a full
  calendar library. No host date API (`java.time`/`js/Date`)."
  [s]
  (let [[y m d] (mapv parse-nonneg-int (str/split s #"-"))]
    (+ (* 365 (dec y))
       (quot (dec y) 4) (- (quot (dec y) 100)) (quot (dec y) 400)
       (nth cumulative-days-before-month (dec m))
       (if (and (> m 2) (leap-year? y)) 1 0)
       (dec d))))

(defn days-between
  "How many days after `from` (an ISO-8601 \"YYYY-MM-DD\" string) does
  `to` fall? Negative when `to` is before `from`."
  [from to]
  (- (day-ordinal to) (day-ordinal from)))

(def annual-return-late-fee-grace-days
  "DICA's own 'Annual Return' guidance page (dica.gov.mm/en/annual-return,
  own text, fetched 2026-07-23): a late fee applies for filings made
  within this many days after the AR due date shown in the company's
  own MyCO profile; beyond this window the company's status becomes
  'Suspended' (DICA's own page uses this English word directly inside
  its Burmese text). This figure is a Burmese-numeral-glyph value
  ('၂၈') transcribed directly from the primary page -- unambiguous
  regardless of translation quality (see `marketentry.facts` epistemic
  caveat)."
  28)

(defn annual-return-status
  "Ground truth Annual Return compliance TIER for `engagement`,
  independently recomputed from its own declared `:ar-due-date` and
  `:ar-filed-date` (plain ISO-8601 \"YYYY-MM-DD\" strings) via a pure
  day-ordinal subtraction -- grounds this vertical's flagship check.
  Returns :current (filed on or before the due date), :late-fee-due
  (filed within the 28-day grace window -- DICA's own 100,000 MMK late
  fee applies), or :filed-after-suspension (filed beyond the grace
  window, meaning DICA would already have marked the company
  'Suspended' before it finally filed); nil when either date is missing
  (indeterminate, never treated as a violation -- the same discipline
  `legislative-approval-authority` (LAO sibling) uses for its own gap
  band)."
  [{:keys [ar-due-date ar-filed-date]}]
  (when (and ar-due-date ar-filed-date)
    (let [delta (days-between ar-due-date ar-filed-date)]
      (cond
        (<= delta 0) :current
        (<= delta annual-return-late-fee-grace-days) :late-fee-due
        :else :filed-after-suspension))))

(defn annual-return-status-mismatch?
  "Does `engagement` declare a `:claimed-ar-status` that does NOT match
  the independently recomputed `annual-return-status`? Entity/engagement-
  scope-gated the same discipline `legislative-approval-mismatch?` (LAO
  sibling) uses: an engagement with no claimed status at all is never
  flagged, and a recompute that returns `nil` (missing dates) is never
  treated as a mismatch either -- this check only fires when it can
  positively demonstrate the claim is wrong."
  [{:keys [claimed-ar-status] :as engagement}]
  (boolean (and claimed-ar-status
                (let [recomputed (annual-return-status engagement)]
                  (and (some? recomputed) (not= claimed-ar-status recomputed))))))

;; ------------------- register-draft / register-submit -------------------

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a MyCO / MIC filing
  package. Pure function -- does not touch any real government system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a MyCO
  Annual Return / MIC permit filing (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
