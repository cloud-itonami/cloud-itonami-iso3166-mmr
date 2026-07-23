# Business Model — Republic of the Union of Myanmar

## Offer

- Directorate of Investment and Company Administration (DICA), Ministry
  of Investment and Foreign Economic Relations (MIFER) -- operates
  Myanmar Companies Online (MyCO) for company registration under the
  Myanmar Companies Law 2017 and the Myanmar Companies Regulations 2018
  (own text, `myco.dica.gov.mm`, 83,850 registrations as of this
  session's fetch)
- Myanmar Investment Commission (MIC), administered by DICA -- investment
  permits/endorsements under the Myanmar Investment Law. DICA's own text:
  "The Myanmar Investment Commission (MIC) is responsible for verifying
  and approving investment proposals," with a proposal "accepted within
  15 days" if it meets criteria and a decision "within 60 days" after
  acceptance (no article/section number given on DICA's own reachable
  pages -- see `src/marketentry/facts.cljc` for the full honest-gap
  account)
- Annual Return (AR) staged-deadline gate (flagship check) -- bars a
  filing that claims the wrong AR compliance status for its own declared
  filing date relative to DICA's own 28-day late-fee grace window (own
  text, `dica.gov.mm/en/annual-return`: 50,000 MMK filing fee, 100,000
  MMK late fee within 28 days, "Suspended" status beyond that, "Struck
  Off" after 6 further months of continued non-filing while suspended).
  Independently corroborated as currently, actively enforced by DICA's
  own real, dated struck-off notifications (e.g. Notification No.65/2026,
  14 Jul 2026)
- market entry via MyCO company registration + MIC investment
  permit/endorsement, not direct competitive bidding for most sectors

## Trust Controls

- Any actual MyCO Annual Return or MIC filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off.
- A false or fabricated regulatory-requirement claim is a HARD hold.
- `:filing/submit` never automated.

## Honest scope limitation

Myanmar's post-2021 institutional situation made primary-source access
genuinely hard this session: DICA's own Laws/Rules/Notifications document
repository returned "No results" to every fetch attempted (a
client-side-rendered listing this session's tooling could not populate),
the Internet Archive Wayback Machine fallback was categorically
unreachable by this session's tooling, and ILO NATLEX/NORMLEX, UNHCR
Refworld, FAO FAOLEX, and MIMU each returned HTTP 403. No
article/section-numbered statutory citation appears anywhere in this
repo's catalogs as a result -- every legal-basis field states plainly
what was and was not independently confirmed this session. This is a
narrower catalog than most `cloud-itonami-iso3166-*` siblings by design,
not by oversight.
