package com.example.moviesmanager.service

import com.example.moviesmanager.activity.MainActivity
import com.example.moviesmanager.model.Movie
import com.example.moviesmanager.model.dao.MovieDAO
import com.example.moviesmanager.model.sqlite.SqliteImpl

class MovieService (mainActivity: MainActivity) {
    private val movieImpl: MovieDAO = SqliteImpl(mainActivity)
    fun insertMovie(movie: Movie) = movieImpl.createMovie(movie)
    fun getMovie(id: Int) = movieImpl.retrieveMovie(id)
    fun getMovies() = movieImpl.retrieveMovies()
    fun editMovie(movie: Movie) = movieImpl.updateMovie(movie)
    fun removeMovie(id: Int) = movieImpl.deleteMovie(id)
}