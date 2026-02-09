package com.example.proyecto_android.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyecto_android.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

//Métodos para acceder a la base de datos
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPelicula(pelicula: MovieEntity)

    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerPeliculas(): Flow<List<MovieEntity>>

    @Update
    suspend fun actualizarPelicula(movie: MovieEntity)

    @Delete
    suspend fun eliminarPelicula(pelicula: MovieEntity)
}
