package com.mobilejavaapp.dictionary.feature_dictionary.data.remote.dto

data class PhoneticDTO(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)