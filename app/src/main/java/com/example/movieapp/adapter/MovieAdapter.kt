package com.example.movieapp.adapter

import android.R
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.model.response.Results


class MovieAdapter(private val listener: OnMovieClickListener) :
    ListAdapter<Results, MovieAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val listener: OnMovieClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Results) {
            Glide.with(itemView.context)
                .load(Constants.posterUrl + movie.backdropPath)
                .into(binding.imgMovie)
            binding.tvMovieTitle.text = movie.title
            binding.apply {
                root.setOnClickListener {
                    listener.onMovieClick(movie)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Results)
    }
}