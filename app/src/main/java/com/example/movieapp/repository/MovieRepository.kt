package com.example.movieapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.model.response.Movie
import com.example.movieapp.model.response.Results
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {

    suspend fun getMovies(): NetworkResult<Movie>{
        return withContext(Dispatchers.IO) {
            val response = movieApiService.getPopularMovies()
              if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkResult.Success(responseBody)
                } else {
                    NetworkResult.Error("And error occurred.")
                }
            } else {
                NetworkResult.Error("And error occurred.")
            }
        }
    }

    suspend fun getMovieDetailById(movieId:String): NetworkResult<Results> {
        return withContext(Dispatchers.IO) {
            val response = movieApiService.getMovieDetailById(movieId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkResult.Success(responseBody)
                } else {
                    NetworkResult.Error("And error occurred.")
                }
            } else {
                NetworkResult.Error("And error occurred.")
            }
        }
    }

    suspend fun searchMovie(searchText:String): NetworkResult<Movie> {
        return withContext(Dispatchers.IO) {
            val response = movieApiService.searchMovie(searchText)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkResult.Success(responseBody)
                } else {
                    NetworkResult.Error("And error occurred.")
                }
            } else {
                NetworkResult.Error("And error occurred.")
            }
        }
    }
}