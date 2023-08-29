package com.mobilejavaapp.dictionary.feature_dictionary.data.remote.dto

import com.mobilejavaapp.dictionary.feature_dictionary.domain.model.Meaning

data class MeaningDTO(
    val antonyms: List<Any>,
    val definitions: List<DefinitionDTO>,
    val partOfSpeech: String,
    val synonyms: List<String>
){
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}