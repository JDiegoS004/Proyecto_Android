package com.example.proyecto_android.di

import android.content.Context
import androidx.room.Room
import com.example.proyecto_android.data.local.dao.MovieDao
import com.example.proyecto_android.data.local.database.MovieDatabase
import com.example.proyecto_android.data.remote.datasource.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Módulo para la inyección de dependencias
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //URL de la API
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                "movie_db"
            ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }
}
