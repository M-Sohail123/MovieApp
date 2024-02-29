package com.example.movieapp.network

import com.example.movieapp.constants.Constants
import com.example.movieapp.model.response.Movie
import com.example.movieapp.model.response.Results
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular?api_key=" + Constants.apiKey)
    suspend fun getPopularMovies(): Movie

    @GET("movie/{movieId}?api_key=" + Constants.apiKey)
    suspend fun getMovieDetailById(@Path("movieId") movieId: String): Results

    @GET("search/movie?api_key=" + Constants.apiKey)
    suspend fun searchMovie(@Query("query") query: String): Movie
}