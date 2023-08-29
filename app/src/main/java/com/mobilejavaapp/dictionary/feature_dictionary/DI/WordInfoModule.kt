package com.mobilejavaapp.dictionary.feature_dictionary.DI

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.mobilejavaapp.dictionary.feature_dictionary.data.local.Converters
import com.mobilejavaapp.dictionary.feature_dictionary.data.local.WordInfoDAO
import com.mobilejavaapp.dictionary.feature_dictionary.data.local.WordInfoDataBase
import com.mobilejavaapp.dictionary.feature_dictionary.data.remote.DictionaryAPI
import com.mobilejavaapp.dictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.mobilejavaapp.dictionary.feature_dictionary.data.util.GsonParser
import com.mobilejavaapp.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.mobilejavaapp.dictionary.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository = repository)
    }

    @Singleton
    @Provides
    fun provideWordInfoRepository(
        db: WordInfoDataBase,
        api: DictionaryAPI
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDataBase {
        return Room.databaseBuilder(
            app,
            WordInfoDataBase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryAPI(): DictionaryAPI {
        return Retrofit.Builder()
            .baseUrl(DictionaryAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryAPI::class.java)
    }
}