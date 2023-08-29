package com.mobilejavaapp.dictionary.feature_dictionary.data.repository

import com.mobilejavaapp.dictionary.core.util.Resource
import com.mobilejavaapp.dictionary.feature_dictionary.data.local.WordInfoDAO
import com.mobilejavaapp.dictionary.feature_dictionary.data.remote.DictionaryAPI
import com.mobilejavaapp.dictionary.feature_dictionary.domain.model.WordInfo
import com.mobilejavaapp.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryAPI,
    private val dao: WordInfoDAO
): WordInfoRepository {

    //Never display data directly from the API,
   // Always add it to the database and then query the database
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong",
                data = wordInfos
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = wordInfos
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}