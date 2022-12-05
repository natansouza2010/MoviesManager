package com.example.moviesmanager.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesmanager.R
import com.example.moviesmanager.adapter.MovieAdapter
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constant
import com.example.moviesmanager.model.Constant.EXTRA_MOVIE
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

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK ) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)
                val delMovie = result.data?.getParcelableExtra<Movie>(Constant.DELETE_MOVIE)

                movie?.let { _movie->
                    val position = movieList.indexOfFirst { it.id == _movie.id }
                    if (position != -1) {
                        // Alterar na posição
                        movieList[position] = _movie
                    }
                    else {
                        movieList.add(_movie)
                    }
                    movieAdapter.notifyDataSetChanged()
                }

                delMovie?.let{
                        _movie ->
                    movieList.remove(_movie);
                    movieAdapter.notifyDataSetChanged()
                }


            }
        }

        movieAdapter.mOnItemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(index: Int, movie: Movie) {
                navigateToEditFiLme(this@MainActivity, movie )
            }

        }

        registerForContextMenu(amb.recycleViewMovies)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMovie -> {
                carl.launch(Intent(this, MovieActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }


    fun navigateToEditFiLme(ctx: Context, movie: Movie) {
        val movieIntent = Intent(ctx ,MovieActivity::class.java )
        movieIntent.putExtra(EXTRA_MOVIE, movie )
        movieIntent.putExtra(Constant.VIEW_MOVIE, true)
        carl.launch(movieIntent)

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
                    genre = Genres.Romance,
                )
            )
        }

        }


}