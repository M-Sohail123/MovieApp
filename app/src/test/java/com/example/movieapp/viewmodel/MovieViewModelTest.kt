package com.example.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieapp.model.response.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.movieapp.model.response.Results
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var movieViewModel: MovieViewModel

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

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        movieViewModel = MovieViewModel(movieRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shouldCheckMovieListSizeIsNotZeroWhenGetMoviesFromServer`() = runTest {
        val dummyMovies = Movie(page = 1,results = arrayListOf(result) , totalPages =10,totalResults=20)
        Mockito.`when`(movieRepository.getMovies()).thenReturn(NetworkResult.Success(dummyMovies))

        movieViewModel.getMovies()
        testDispatcher.scheduler.advanceUntilIdle()

        val resultLiveData = movieViewModel.movies.value
        Assert.assertEquals(dummyMovies, resultLiveData?.data)
        Assert.assertEquals(1, resultLiveData?.data?.results!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shouldCheckMovieSizeZeroWhenGetMoviesReturnFromServer`() = runTest {
        val dummyMovies = Movie()
        Mockito.`when`(movieRepository.getMovies()).thenReturn(NetworkResult.Success(dummyMovies))

        movieViewModel.getMovies()
        testDispatcher.scheduler.advanceUntilIdle()

        val resultLiveData = movieViewModel.movies.value
        Assert.assertEquals(dummyMovies, resultLiveData?.data)
        Assert.assertEquals(0, resultLiveData?.data?.results!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shouldCheckMovieDetailsFetchWhenGetMovieDetailsByIdReturnFromServer`() = runTest {
        val dummyMovieId = "123"
        val dummyMovieDetail = result
        Mockito.`when`(movieRepository.getMovieDetailById(dummyMovieId)).thenReturn(NetworkResult.Success(dummyMovieDetail))

        movieViewModel.getMovieDetailsById(dummyMovieId)
        testDispatcher.scheduler.advanceUntilIdle()

        Assert.assertEquals(dummyMovieDetail, movieViewModel.movieDetail.value?.data)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shouldsearchMovieByTextWhenSearchMovieClick`() = runTest {
        val dummySearchText = "Avengers"
        val dummySearchResult = Movie(page = 1,results = arrayListOf(result) , totalPages =10,totalResults=20)
        Mockito.`when`(movieRepository.searchMovie(dummySearchText)).thenReturn(NetworkResult.Success(dummySearchResult))

        movieViewModel.searchMovie(dummySearchText)
        testDispatcher.scheduler.advanceUntilIdle()

        val resultLiveData = movieViewModel.searchMovie.value
        Assert.assertEquals(dummySearchResult, resultLiveData?.data)
        Assert.assertEquals(1, resultLiveData?.data?.results!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shouldCheckSearchMovieSizeIsZeroWhenSearchMovieClick`() = runTest {
        val dummySearchText = "Avengers"
        val dummySearchResult = Movie()
        Mockito.`when`(movieRepository.searchMovie(dummySearchText)).thenReturn(NetworkResult.Success(dummySearchResult))

        movieViewModel.searchMovie(dummySearchText)
        testDispatcher.scheduler.advanceUntilIdle()

        val resultLiveData = movieViewModel.searchMovie.value
        Assert.assertEquals(dummySearchResult, resultLiveData?.data)
        Assert.assertEquals(0, resultLiveData?.data?.results!!.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}