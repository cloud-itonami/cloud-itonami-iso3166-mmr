(ns statute.facts
  "General-law compliance catalog for the Republic of the Union of
  Myanmar (MMR) -- extends this repo's existing `marketentry.facts`
  (public-sector market-entry/foreign-investment only, narrow scope)
  with a second, orthogonal catalog of national statutes a foreign
  investor operating in this jurisdiction must generally track for
  compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-idn/-lao/-brn's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-
  federation).

  Every entry cites a URL -- never fabricated. This catalog is
  DELIBERATELY narrower than most siblings: see `marketentry.facts` for
  the full account of why Myanmar primary-source access was genuinely
  hard this session (DICA's own Laws/Rules/Notifications document
  repository returned 'No results' to every fetch attempted; the
  Internet Archive Wayback Machine fallback was categorically
  unreachable by this session's tooling; ILO NATLEX/NORMLEX, UNHCR
  Refworld, FAO FAOLEX, and MIMU each returned HTTP 403; UNCTAD's
  Investment Laws Navigator could not be resolved to Myanmar's specific
  entry this session).

  - **Myanmar Investment Law**: the SAME law `marketentry.facts` uses as
    its market-entry spec-basis, ALSO catalogued here as a general
    national-law reference since a foreign investor tracks it both as a
    market-entry gate and as an ongoing compliance statute. Named
    repeatedly, live, on DICA's own official site (dica.gov.mm, own
    text, read directly, fetched 2026-07-23) with NO law-number/date
    stated on any DICA page reachable this session. The unofficial
    compilation site `myanmar-law-library.org` (self-declared unofficial,
    CC BY-SA 4.0 licensed -- own text, read directly, fetched
    2026-07-23) titles it 'Myanmar Investment Law n°40/2016' and states
    it 'combined the Myanmar Citizen's Investment Law (2013) with the
    Foreign Investment Law (2012)'. This iteration did NOT independently
    confirm the 'No.40/2016' designation or an enactment date via a
    primary government source this session -- cited here as an
    UNOFFICIAL secondary claim, not a primary-source-confirmed fact
    (the same honest-gap discipline `cloud-itonami-iso3166-lao`'s own
    facts.cljc used for its Income Tax Decree: existence and current
    applicability are HIGH confidence, own law-number precision is NOT
    independently confirmed).
  - **Myanmar Companies Law 2017**: DICA's own official site names this
    law repeatedly and currently (own text, read directly, fetched
    2026-07-23, e.g. 'In accordance with the Myanmar Companies Law
    2017, a company may be registered in one of the following forms
    ...') as the governing company-registration statute, and DICA's own
    Myanmar Companies Online (MyCO) portal (myco.dica.gov.mm, own text,
    read directly) additionally names 'The Myanmar Companies Regulations
    2018' as an implementing instrument. `myanmar-law-library.org`
    (unofficial, same caveat as above) states the law was 'signed by the
    president on the 6th December' 2017 with commencement postponed to
    August 2018 'to let the DICA and the MIC be fully operational' --
    this iteration did NOT independently confirm the exact Pyidaungsu
    Hluttaw law number via a primary government source reachable this
    session.
  - **Labour law**: this iteration specifically searched for Myanmar's
    own general labour/employment statute and could NOT independently
    locate or confirm its own law-number, date, or text this session --
    DICA's own dedicated 'Labour' policy page
    (dica.gov.mm/dica/policy-and-law/labour, own text, read directly)
    only mentions the Myanmar Companies Law and Myanmar Investment Law
    in passing relation to employer obligations, naming NO dedicated
    labour statute; ILO NATLEX/NORMLEX (the standard international index
    for exactly this kind of lookup) returned HTTP 403 Forbidden to
    every URL tried this session. This catalog does NOT carry a labour-
    law entry as a result -- an honestly-reported gap, the same
    discipline `cloud-itonami-iso3166-lao`'s own facts.cljc used for its
    own jurisdiction's Labour Law.
  - **Tax law**: `myanmar-law-library.org`'s own tax-law topic index
    (own text, read directly, fetched 2026-07-23, before that domain
    began rate-limiting this session's requests -- see
    `marketentry.facts`) lists CATEGORY NAMES ONLY -- 'Income Tax Law',
    'Union Taxation Law', 'Commercial Tax Law', 'Specific Goods Tax Law',
    'Stamp Duty', 'Tax Administration Law' -- with no law numbers, dates,
    or individual document links surfaced on that index page within this
    session's fetch. This catalog does NOT carry a tax-law entry as a
    result -- category names alone, from a single unofficial secondary
    source, are not enough to cite a specific statute honestly.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"MMR"
   [{:statute/id "mmr.investment-law"
     :statute/title "Myanmar Investment Law"
     :statute/jurisdiction "MMR"
     :statute/kind :law
     :statute/law-number "Named repeatedly, live, on DICA's own official site (dica.gov.mm) with NO law-number or enactment date stated on any DICA page reachable this session; the unofficial compilation site myanmar-law-library.org titles it 'Myanmar Investment Law n°40/2016' and states it combined the Myanmar Citizen's Investment Law (2013) with the Foreign Investment Law (2012) -- this iteration could NOT independently confirm the 'No.40/2016' designation or an enactment date via a primary government source this session (an honest gap, not a fabricated citation)"
     :statute/url "https://www.dica.gov.mm/en"
     :statute/url-provenance :official-dica-gov-mm
     :statute/enacted-date nil
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:foreign-investment :corporate-governance}}
    {:statute/id "mmr.companies-law-2017"
     :statute/title "Myanmar Companies Law 2017"
     :statute/jurisdiction "MMR"
     :statute/kind :law
     :statute/law-number "Named repeatedly, live, on DICA's own official site (dica.gov.mm) with NO Pyidaungsu Hluttaw law number stated on any DICA page reachable this session; the unofficial compilation site myanmar-law-library.org states it was 'signed by the president on the 6th December' 2017, commencement postponed to August 2018 'to let the DICA and the MIC be fully operational' -- this iteration could NOT independently confirm the exact law number via a primary government source this session (an honest gap, not a fabricated citation)"
     :statute/url "https://www.dica.gov.mm/en/register-your-company"
     :statute/url-provenance :official-dica-gov-mm
     :statute/enacted-date "2017-12-06"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "mmr.companies-regulations-2018"
     :statute/title "Myanmar Companies Regulations 2018"
     :statute/jurisdiction "MMR"
     :statute/kind :decree
     :statute/law-number "Named on DICA's own official Myanmar Companies Online (MyCO) portal (myco.dica.gov.mm, own text, read directly, fetched 2026-07-23) as 'The Myanmar Companies Regulations 2018', the implementing regulation for the Myanmar Companies Law 2017 -- this iteration could NOT independently confirm a specific regulation/notification number or exact promulgation date this session (an honest gap, not a fabricated citation)"
     :statute/url "https://www.myco.dica.gov.mm/"
     :statute/url-provenance :official-myco-dica-gov-mm
     :statute/enacted-date nil
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-mmr statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "MMR")) " MMR statute(s) seeded with a "
                 "citation. Deliberately narrower than most siblings -- labour "
                 "law and tax law could not be independently confirmed with a "
                 "citable law number/date this session (see namespace "
                 "docstring). Extend `statute.facts/catalog`, never fabricate "
                 "a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
