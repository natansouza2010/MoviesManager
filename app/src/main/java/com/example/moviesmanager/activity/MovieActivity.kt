package com.example.moviesmanager.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.databinding.ActivityMovieBinding
import com.example.moviesmanager.model.Constant.DELETE_MOVIE
import com.example.moviesmanager.model.Constant.EXTRA_MOVIE
import com.example.moviesmanager.model.Movie
import com.example.moviesmanager.model.enums.Genres
import java.util.*
import kotlin.math.log

class MovieActivity : AppCompatActivity() {

   private val amb: ActivityMovieBinding by lazy {
       ActivityMovieBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)



        receivedMovie?.let{ _receivedMovie ->
            with(amb) {
                with(_receivedMovie) {
                   inputName.setText(name)
                    inputName.isEnabled = false
                    inputYear.setText(year.toString())
                    inputStudio.setText(studio)
                    inputTimeOfDuration.setText(timeOfDuration.toString())
                    if(hasWatched) watched.isChecked =true else unattended.isChecked = hasWatched

                    amb.spinnerNotes.setSelection(note);
                    for (i in  0 until Genres.values().size) {
                        if(genre.toString().equals(Genres.values()[i].toString()))
                            spinnerGenres.setSelection(i)
                    }







                }
            }
        }


        amb.btnSaveOrUpdateMovie.setOnClickListener {
            val isChecked = if(amb.watched.isChecked) true else false
            val genrer = amb.spinnerGenres.selectedItem.toString()
            val genrerFromEnum = Genres.valueOf(genrer)
            val movie = Movie (
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = amb.inputName.text.toString(),
                year = amb.inputYear.text.toString().toInt(),
                studio = amb.inputStudio.text.toString(),
                timeOfDuration = amb.inputTimeOfDuration.text.toString().toInt(),
                hasWatched = isChecked,
                note = amb.spinnerNotes.selectedItem.toString().toInt(),
                genre = genrerFromEnum,

            )

            print(movie.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        amb.btnDeleteMovie.setOnClickListener {
            if(receivedMovie != null) {
                val resultIntent = Intent()
                resultIntent.putExtra(DELETE_MOVIE, receivedMovie)
                setResult(RESULT_OK, resultIntent)
                finish()

            }


        }
    }




}