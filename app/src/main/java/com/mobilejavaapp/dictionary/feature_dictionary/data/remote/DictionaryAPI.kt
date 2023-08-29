package com.mobilejavaapp.dictionary.feature_dictionary.data.remote

import com.mobilejavaapp.dictionary.feature_dictionary.data.remote.dto.WordInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(@Path("word") word: String): List<WordInfoDTO>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev"
    }
}