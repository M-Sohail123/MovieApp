package com.example.movieapp.screen.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel by viewModels<MovieViewModel>() //No need of inject annotation.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        if (arguments != null) {
            val movieId = arguments.getString("argData")
            movieId?.let {
                viewModel.getMovieDetailsById(movieId)
            }
        }

        viewModel.movieDetail.observe(viewLifecycleOwner) { movies ->
            try{
            movies?.let {
                binding.tvMovieTitle.text = it.data?.title
                binding.tvMovieDesc.text = it.data?.overview
                Glide.with(requireContext())
                    .load(Constants.posterUrl + it.data?.backdropPath)
                    .into(binding.imgMovie)
            }
            }catch (e:Exception){

            }
        }
    }
}