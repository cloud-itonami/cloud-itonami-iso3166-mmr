(ns culture.facts
  "Country-level regional-culture catalog for Myanmar (MMR) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"MMR"
   [{:culture/id "mmr.dish.mohinga"
     :culture/name "Mohinga"
     :culture/country "MMR"
     :culture/kind :dish
     :culture/summary "National dish of Myanmar; a fish soup made with rice noodles, typically served as a hearty breakfast."
     :culture/url "https://en.wikipedia.org/wiki/Mohinga"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.dish.lahpet"
     :culture/name "Lahpet"
     :culture/name-local "လက်ဖက်"
     :culture/country "MMR"
     :culture/kind :dish
     :culture/summary "Burmese fermented or pickled tea leaves, regarded as a national delicacy in Myanmar and eaten as the salad laphet thoke or served as a snack."
     :culture/url "https://en.wikipedia.org/wiki/Lahpet"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.dish.ohn-no-khao-swe"
     :culture/name "Ohn no khao swè"
     :culture/country "MMR"
     :culture/kind :dish
     :culture/summary "Burmese dish of wheat noodles in a curried chicken and coconut milk broth thickened with gram flour, believed to originate in Shwedaung near Pyay and traditionally served for breakfast or brunch."
     :culture/url "https://en.wikipedia.org/wiki/Ohn_no_khao_sw%C3%A8"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.beverage.palm-wine"
     :culture/name "Palm wine (htan yay)"
     :culture/name-local "ထန်းရည်"
     :culture/country "MMR"
     :culture/kind :beverage
     :culture/summary "Palm wine, called htan yay in Burmese, is made from the fermented sap of the toddy palm and is traditionally consumed in rural parts of Upper Myanmar's Dry Zone."
     :culture/url "https://en.wikipedia.org/wiki/Palm_wine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.craft.lacquerware"
     :culture/name "Burmese lacquerware"
     :culture/country "MMR"
     :culture/kind :craft
     :culture/summary "Burmese lacquerware (yun-de) is a distinct Southeast Asian lacquerware tradition originating in Myanmar, described in a dedicated section of Wikipedia's Lacquerware article covering its history, manufacture and forms."
     :culture/url "https://en.wikipedia.org/wiki/Lacquerware"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.product.longyi"
     :culture/name "Longyi"
     :culture/country "MMR"
     :culture/kind :product
     :culture/summary "A sheet of cloth roughly 2 metres long and 80 centimetres wide, widely worn in Myanmar sewn into a cylindrical shape and worn around the waist to the feet."
     :culture/url "https://en.wikipedia.org/wiki/Longyi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.festival.thingyan"
     :culture/name "Thingyan"
     :culture/country "MMR"
     :culture/kind :festival
     :culture/summary "Myanmar's traditional New Year water festival occurring in mid-April, based on the lunisolar calendar and inscribed as UNESCO Intangible Cultural Heritage in 2024."
     :culture/url "https://en.wikipedia.org/wiki/Thingyan"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "mmr.heritage.bagan"
     :culture/name "Bagan"
     :culture/country "MMR"
     :culture/kind :heritage
     :culture/summary "Ancient city in Myanmar's Mandalay Region, officially inscribed as a UNESCO World Heritage Site in 2019, the country's second such designation."
     :culture/url "https://en.wikipedia.org/wiki/Bagan"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-mmr culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "MMR"))
                 " MMR entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
