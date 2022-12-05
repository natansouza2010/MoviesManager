package com.example.moviesmanager.model.dao

import com.example.moviesmanager.model.Movie

interface MovieDAO {
    fun createMovie(movie: Movie): Int
    fun retrieveMovie(movieId: Int): Movie?
    fun retrieveMovies(): MutableList<Movie>
    fun updateMovie(movie: Movie): Int
    fun deleteMovie(idMovie: Int): Int
}
