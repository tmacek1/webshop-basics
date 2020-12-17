package com.assignment.webshop.basics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HnbDTO {

    @JsonProperty("Broj tečajnice")
    private String brojTecajnice;

    @JsonProperty("Datum primjene")
    private String datumPrimjene;

    @JsonProperty("Država")
    private String drzava;

    @JsonProperty("Šifra valute")
    private String sifraValute;

    @JsonProperty("Valuta")
    private String valuta;

    @JsonProperty("Jedinica")
    private int jedinica;

    @JsonProperty("Kupovni za devize")
    private String kupovniZaDevize;

    @JsonProperty("Srednji za devize")
    private String srednjiZaDevize;

    @JsonProperty("Prodajni za devize")
    private String prodajniZaDevize;

}
