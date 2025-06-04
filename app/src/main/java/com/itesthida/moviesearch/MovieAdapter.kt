package com.itesthida.moviesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itesthida.moviesearch.data.Movie
import com.itesthida.moviesearch.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    var items : List<Movie>,
    val onItemClick : (position : Int) -> Unit
) : Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.render(movie)

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    fun updateItems(items : List<Movie>){
        // Los items son iguales a los items recibidos como parámetro
        this.items = items
        // Notificamos al adapter que el conjunto de datos ha cambiado
        notifyDataSetChanged()
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) : ViewHolder(binding.root){
    fun render(movie: Movie){
        binding.movieTitle.text = movie.Title
        Picasso.get().load(movie.Poster).into(binding.ivPoster)
    }
}