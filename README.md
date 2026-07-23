# cloud-itonami-iso3166-mmr

Open ISO 3166 Blueprint for **MMR**: Republic of the Union of Myanmar.

- Directorate of Investment and Company Administration (DICA), Ministry
  of Investment and Foreign Economic Relations (MIFER) -- operates
  Myanmar Companies Online (MyCO) for company registration under the
  Myanmar Companies Law 2017, and administers Myanmar Investment
  Commission (MIC) investment permits/endorsements
- Annual Return (AR) compliance gate (flagship check) -- bars a filing
  that claims the wrong Annual Return status for its own declared filing
  date relative to DICA's own 28-day late-fee grace window

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as `cloud-itonami-iso3166-idn`/`-lao`/`-brn`. Myanmar's institutional
situation since 2021 made primary-source access a genuinely hard
research task this iteration (verified 2026-07-23) -- see the namespace
docstrings for the full research trail, including everything this
iteration could NOT verify. **This catalog is deliberately narrower and
more heavily caveated than most siblings; omission was chosen over
fabrication throughout.**

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites DICA's own live
  official site (`dica.gov.mm`, actively maintained, most recent content
  dated 2026-07-17 as of this session's fetch) for the Myanmar Companies
  Law 2017 / Myanmar Investment Law / MIC permit process, and DICA's own
  Myanmar Companies Online portal (`myco.dica.gov.mm`) for the Myanmar
  Companies Regulations 2018. DICA's own document repository (the actual
  numbered law/notification PDFs) returned "No results" to every fetch
  attempt this session (a client-side-rendered listing this session's
  tooling could not populate) -- no article/section-numbered statutory
  citation appears anywhere in this catalog as a result, an honest gap,
  not an oversight. This session's Internet Archive Wayback Machine
  fallback was also categorically unreachable by this session's tooling.
  `governor.cljc`'s flagship check independently recomputes a company's
  own declared Annual Return filing date against DICA's own described
  28-day late-fee grace window and "Suspended"/"Struck Off" consequence
  chain (`dica.gov.mm/en/annual-return`, own text -- note this specific
  page renders in Burmese despite its `/en/` URL, see the `facts.cljc`
  docstring for the full epistemic caveat on the resulting translation).
  This mechanism is independently corroborated as currently, actively
  enforced by DICA's own real, dated struck-off notifications (as recent
  as 14 Jul 2026) -- a THREE-TIER staged deadline/grace-window
  classification, a genuinely different check shape from every sibling
  this iteration's author read (`idn`'s field-presence boolean, `lao`'s
  legislative authority-routing classification, `brn`'s bare precedence
  test).
- `src/statute/facts.cljc` -- general-law catalog: the Myanmar
  Investment Law, the Myanmar Companies Law 2017, and the Myanmar
  Companies Regulations 2018. This catalog deliberately carries NO
  labour-law entry and NO tax-law entry -- ILO NATLEX/NORMLEX returned
  HTTP 403 to every URL tried, and DICA's own dedicated Labour page names
  no dedicated labour statute; a secondary source's tax-law index
  surfaced category names only (no law numbers/dates) before that
  domain began rate-limiting this session's requests. Honestly-reported
  gaps, not fabricated citations (see namespace docstring).

Every citation is directly fetched and read this session
(`dica.gov.mm`, `myco.dica.gov.mm`); the unofficial secondary compilation
`myanmar-law-library.org` (self-declared unofficial, CC BY-SA 4.0) is
used ONLY for law name/date/number claims DICA's own reachable pages did
not themselves state, and is labeled as unofficial everywhere it is
cited. DICA's own site operates under the Ministry of Investment and
Foreign Economic Relations (MIFER) -- the ministry name used by
Myanmar's current State Administration Council-led administration (in
power since the February 2021 military takeover); this catalog documents
the current, live, de facto administrative practice a business must
actually interact with, without taking a position on the contested
international legitimacy of that government.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Myanmar:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
