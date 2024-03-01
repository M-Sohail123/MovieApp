package com.example.movieapp.repository

import com.example.movieapp.model.response.Movie
import com.example.movieapp.model.response.Results
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.util.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import org.junit.*
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    private val mockApiService = mock(MovieApiService::class.java)
    private val repository = MovieRepository(mockApiService)

    val result = Results(
        adult = false,
        backdropPath = "/4woSOUD0equAYzvwhWBHIJDCM88.jpg",
        genreIds = arrayListOf(28, 27, 53),
        id = 1096197,
        originalLanguage = "en",
        originalTitle = "No Way Up",
        overview = "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
        popularity = 2426.543,
        posterPath = "/7FpGJTN8IL6IBvQMp6YHBFyhO9Z.jpg",
        releaseDate = "2024-01-18",
        title = "No Way Up",
        video = false,
        voteAverage = 5.78,
        voteCount = 75
    )

    @Test
    fun `shouldGetMoviesWhenApiResponseSuccess`() = runBlocking {
        val mockResponse = Movie(page = 1,results = arrayListOf(result) , totalPages =10,totalResults=20)
        `when`(mockApiService.getPopularMovies()).thenReturn(Response.success(mockResponse))

        val result = repository.getMovies()

        assertEquals(mockResponse, result.data)
        assertEquals(NetworkResult.Success(mockResponse), result)
    }


    @Test
    fun `shouldGetMovieDetailByIdWhenApiResponseSuccess`() = runBlocking {
        val dummyMovieId = "123"
        val dummyMovieDetailMockResponse = result
        `when`(mockApiService.getMovieDetailById(dummyMovieId)).thenReturn(Response.success(dummyMovieDetailMockResponse))

        val result = repository.getMovieDetailById(dummyMovieId)

        assertEquals(dummyMovieDetailMockResponse, result.data)
        assertEquals(NetworkResult.Success(dummyMovieDetailMockResponse), result)
    }


    @Test
    fun `shouldGetMovieByTextWhenApiResponseSuccess`() = runBlocking {
        val dummySearchText = "Avengers"
        val dummySearchResult = Movie(page = 1,results = arrayListOf(result) , totalPages =10,totalResults=20)

        `when`(mockApiService.searchMovie(dummySearchText)).thenReturn(Response.success(dummySearchResult))

        val result = repository.searchMovie(dummySearchText)

        assertEquals(dummySearchResult, result.data)
        assertEquals(NetworkResult.Success(dummySearchResult), result)
    }

}