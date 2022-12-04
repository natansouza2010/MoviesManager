package com.example.moviesmanager.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.MovieAdapter
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Movie
import com.example.moviesmanager.model.enums.Genres

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillMovieList()
        movieAdapter = MovieAdapter(movieList, this)
        amb.recycleViewMovies.adapter = movieAdapter
        amb.recycleViewMovies.layoutManager = LinearLayoutManager(applicationContext)


    }


    private fun fillMovieList() {
        for (i in 1..10) {
            movieList.add(
                Movie(
                    id = i,
                    name = "Nome $i",
                    year = i,
                    studio = "Studio $i" ,
                    timeOfDuration= i,
                    hasWatched = true,
                    note = i,
                    genre = Genres.ADVENTURE,
                )
            )
        }

        }


}