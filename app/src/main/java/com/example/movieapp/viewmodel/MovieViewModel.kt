package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.response.Movie
import com.example.movieapp.model.response.Results
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    var _movies = MutableLiveData<NetworkResult<Movie>>()
    val movies: MutableLiveData<NetworkResult<Movie>> get() =  _movies

    var _movieDetail = MutableLiveData<NetworkResult<Results>>()
    val movieDetail: MutableLiveData<NetworkResult<Results>> get() =  _movieDetail

    var _searchMovie = MutableLiveData<NetworkResult<Movie>>()
    val searchMovie: MutableLiveData<NetworkResult<Movie>> get() =  _searchMovie

    init {
        viewModelScope.launch {
            _movies.value =  repository.getMovies()
        }
    }

    fun getMovies(){
        viewModelScope.launch {
            _movies.value = repository.getMovies()
        }
    }
    fun getMovieDetailsById(movieId:String){
        viewModelScope.launch {
            _movieDetail.value = repository.getMovieDetailById(movieId)
        }
    }

    fun searchMovie(searchText:String){
        viewModelScope.launch {
            _searchMovie.value = repository.searchMovie(searchText)
        }
    }
}