package com.itesthida.moviesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.itesthida.moviesearch.data.Movie
import com.itesthida.moviesearch.data.MovieServiceAPI
import com.itesthida.moviesearch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovieAdapter

    var movieList : List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = MovieAdapter(movieList){ position ->
            val movie = movieList[position]
            // Hacemos el intent (navegar al detalle del superheroe)
            val intent = Intent(this, MovieDetailActivity::class.java)

            intent.putExtra("movieId", movie.imdbID)

            startActivity(intent)
        }

        binding.rvMovies.adapter = adapter

        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)


        binding.btnSearchMovie.setOnClickListener {
            val title = binding.filledTextField.editText?.text.toString()
            searchMovies(title)
        }

        // Lamada por defecto
        searchMovies("Superman")
    }

    fun searchMovies(query : String){
        // Llamada en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Llamada al servicio de movies para obtener los items que
                // coincidan con el nombre que recibe por paránmetro
                val service = MovieServiceAPI.getInstance()
                // A través del service llamamos al servicio que busca las movies por nombre
                // Y devuelve un objeto con los resultados
                val response = service.findMovieByName(query)

                // Del SuperHeroSearchResponse, obtenemos el listado de superheroes y se la asignamos
                //  a la propiedad movieList del activity
                movieList = response.results

                Log.i("ESTHIDAIT", "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
                Log.i("ESTHIDAIT", response.toString())
                Log.i("ESTHIDAIT", "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")

                // Volvemos al hilo principal
                CoroutineScope(Dispatchers.Main).launch {
                    // Y le indicamos al adapter que hay nuevos elementos y actualice las movies
                    adapter.updateItems(movieList)

                }

            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}