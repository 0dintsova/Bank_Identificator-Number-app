package com.example.bin.list

data class Card(val scheme: String? = null, val type: String? = null,
           val brand: String? = null, val prepaid: String? = null,
           val cardNumberLength: String? = null, val cardNumberLuhn: String? = null,
           val country : String? = null, val countryLatitude : String? = null, val countryLongitude: String? = null,
           val bankName : String? = null, val bankUrl: String? = null, val bankPhoneNum: String? = null) {
}