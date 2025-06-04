package com.itesthida.moviesearch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.moviesearch.data.Movie
import com.itesthida.moviesearch.data.MovieServiceAPI
import com.itesthida.moviesearch.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId = intent.getStringExtra("movieId")!!
        //Toast.makeText(this, "MovieId: $movieId", Toast.LENGTH_SHORT).show()

        getMovieDetail(movieId)
    }

    fun getMovieDetail(id : String){
        // Llamada en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Llamada al servicio de superheroes para obtener los items que
                // coincidan con el nombre que recibe por paránmetro
                val service = MovieServiceAPI.getInstance()
                // A través del service llamamos al servicio que busca los superheroes por nombre
                // Y devuelve un objeto Superhero
                movie = service.findMovieById(id)

                Log.i("ESTHIDAIT", "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
                Log.i("ESTHIDAIT", movie.toString())
                Log.i("ESTHIDAIT", "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")

                // Volvemos al hilo principal
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()

                }

            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadData() {

        //Toast.makeText(this, movie.Title, Toast.LENGTH_SHORT).show()

        Picasso.get().load(movie.Poster).into(binding.ivPoster)
        binding.tvTitle.text = movie.Title
        binding.tvMovieYear.text = movie.Year
        binding.tvMovieRuntime.text = "${movie.Runtime}"
        binding.tvMovieDirector.text = movie.Director
        binding.tvMovieGenre.text = movie.Genre
        binding.tvMovieCountry.text = movie.Country
        binding.tvMoviePlot.text = movie.Plot

    }
}