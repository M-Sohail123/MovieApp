package com.example.movieapp.screen.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movieapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieListingBinding
import com.example.movieapp.model.response.Results
import com.example.movieapp.screen.fragments.MovieListingFragmentDirections.Companion.actionFragmentMovieListingToFragmentMovieDetail
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListingFragment : Fragment(), MovieAdapter.OnMovieClickListener {
    private lateinit var binding: FragmentMovieListingBinding
    private lateinit var adapter: MovieAdapter
    private val viewModel by viewModels<MovieViewModel>() //No need of inject annotation.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        binding.edtSearchMovie.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchText = textView.text.toString()
                if (searchText.isNotEmpty()) {
                    performSearch(searchText)
                } else {
                    viewModel.getMovies()
                }
                return@setOnEditorActionListener true
            }
            false
        }

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                adapter.submitList(it.data?.results)
            }
        }
        viewModel.searchMovie.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                adapter.submitList(it.data?.results)
            }
        }
    }

    private fun navigateToDetailFragment(movieId: String) {
        var navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        val bundle = Bundle()
        bundle.putString("argData", movieId);
        navController.navigate(R.id.action_fragment_movie_listing_to_fragment_movie_detail, bundle)
    }

    private fun performSearch(searchText: String) {
        viewModel.searchMovie(searchText)
    }

    override fun onMovieClick(movie: Results) {
        navigateToDetailFragment(movie.id.toString())
    }
}