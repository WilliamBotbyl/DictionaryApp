package com.mobilejavaapp.dictionary.feature_dictionary.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
){
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions,
            partOfSpeech = partOfSpeech
        )
    }
}
