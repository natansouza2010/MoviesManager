package com.example.moviesmanager.model.sqlite

import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import java.sql.SQLException
import android.content.Context
import android.util.Log
import com.example.moviesmanager.model.Movie
import com.example.moviesmanager.model.dao.MovieDAO
import com.example.moviesmanager.model.enums.Genres

class SqliteImpl(context: Context): MovieDAO {
    companion object Constant {
        private const val MOVIE_DATABASE_FILE = "movies_database"
        private const val MOVIE_TABLE = "movie"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val YEAR_COLUMN = "year"
        private const val STUDIO_COLUMN = "studio"
        private const val TIME_OF_DURATION_COLUMN = "timeofduration"
        private const val WATCHED_COLUMN = "watched"
        private const val NOTE_COLUMN = "note"
        private const val GENRE_COLUMN = "genre"

        private const val CREATE_MOVIE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $MOVIE_TABLE ( " +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL, " +
                    "$YEAR_COLUMN TEXT NOT NULL, " +
                    "$STUDIO_COLUMN TEXT NOT NULL, " +
                    "$TIME_OF_DURATION_COLUMN TEXT NOT NULL," +
                    "$WATCHED_COLUMN BOOLEAN NOT NULL," +
                    "$NOTE_COLUMN TEXT NOT NULL," +
                    "$GENRE_COLUMN TEXT NOT NULL );"
    }

    // Referência para o banco de dados
    private val movieSqliteDatabase: SQLiteDatabase

    init {
        movieSqliteDatabase = context.openOrCreateDatabase(
            MOVIE_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            movieSqliteDatabase.execSQL(CREATE_MOVIE_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e("SQL_Error", "Erro ao criar banco")
        }
    }

    private fun Movie.toContentValues() = with(ContentValues()) {
        put(NAME_COLUMN, name)
        put(YEAR_COLUMN, year)
        put(STUDIO_COLUMN, studio)
        put(TIME_OF_DURATION_COLUMN, timeOfDuration)
        put(WATCHED_COLUMN, hasWatched )
        put(NOTE_COLUMN, note )
        put(GENRE_COLUMN, genre.toString())


        this
    }

    private fun movieToContentValues(movie: Movie) = with(ContentValues()) {
        put(NAME_COLUMN, movie.name)
        put(YEAR_COLUMN, movie.year)
        put(STUDIO_COLUMN, movie.studio)
        put(TIME_OF_DURATION_COLUMN, movie.timeOfDuration)
        put(WATCHED_COLUMN, movie.hasWatched)
        put(NOTE_COLUMN, movie.note)
        put(GENRE_COLUMN, movie.genre.toString())
        this
    }

    private fun Cursor.rowToMovie() = Movie(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getInt(getColumnIndexOrThrow(YEAR_COLUMN)),
        getString(getColumnIndexOrThrow(STUDIO_COLUMN)),
        getInt(getColumnIndexOrThrow(TIME_OF_DURATION_COLUMN)),
        getColumnIndexOrThrow(WATCHED_COLUMN) == 0,
        getInt(getColumnIndexOrThrow(NOTE_COLUMN)),
        Genres.valueOf(getString(getColumnIndexOrThrow(GENRE_COLUMN)))
    )

    override fun createMovie(movie: Movie) = movieSqliteDatabase.insert(
        MOVIE_TABLE,
        null,
        movieToContentValues(movie)
    ).toInt()


    override fun retrieveMovie(id: Int): Movie? {
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )
        val movie = if (cursor.moveToFirst()) cursor.rowToMovie() else null

        cursor.close()
        return movie
    }

    override fun retrieveMovies(): MutableList<Movie> {
        val movieList = mutableListOf<Movie>()
        val cursor = movieSqliteDatabase.rawQuery(
            "SELECT * FROM $MOVIE_TABLE ORDER BY $NAME_COLUMN",
            null
        )
        while (cursor.moveToNext()) {
            movieList.add(cursor.rowToMovie())
        }
        cursor.close()
        return movieList
    }

    override fun updateMovie(movie: Movie) = movieSqliteDatabase.update(
        MOVIE_TABLE,
        movie.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(movie.id.toString())
    )

    override fun deleteMovie(id: Int) =
        movieSqliteDatabase.delete(
            MOVIE_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString())
        )
}