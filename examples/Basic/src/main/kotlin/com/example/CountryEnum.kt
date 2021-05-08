package com.example

enum class CountryEnum(val countrycode: String) {
    NETHERLANDS("NL"), BELGIUM("BE"), ENGLAND("EN"), GERMANY("DE");

    fun retrieveCountryCodeText() = "Countrycode: $countrycode"


}