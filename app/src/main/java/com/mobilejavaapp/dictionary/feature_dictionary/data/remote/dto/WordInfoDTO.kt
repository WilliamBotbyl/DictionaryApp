package com.mobilejavaapp.dictionary.feature_dictionary.data.remote.dto

import com.mobilejavaapp.dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.mobilejavaapp.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDTO(
    val license: License,
    val meanings: List<MeaningDTO>,
    val phonetic: String,
    val phonetics: List<PhoneticDTO>?,
    val sourceUrls: List<String>,
    val word: String
){
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic?:"",
            word = word
        )
    }
}