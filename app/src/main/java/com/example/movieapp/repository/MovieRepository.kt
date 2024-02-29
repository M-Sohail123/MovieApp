package com.example.movieapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.model.response.Movie
import com.example.movieapp.model.response.Results
import com.example.movieapp.network.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {

    suspend fun getMovies(): Movie {
        return withContext(Dispatchers.IO) {
            movieApiService.getPopularMovies()
        }
    }

    suspend fun getMovieDetailById(movieId:String): Results {
        return withContext(Dispatchers.IO) {
            movieApiService.getMovieDetailById(movieId)
        }
    }

    suspend fun searchMovie(searchText:String): Movie {
        return withContext(Dispatchers.IO) {
            movieApiService.searchMovie(searchText)
        }
    }
}