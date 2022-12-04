package com.example.moviesmanager.model

import android.os.Parcelable
import com.example.moviesmanager.model.enums.Genres
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Movie(
    val id: Int,
    var name: String,
    var year: Int,
    var studio: String,
    var timeOfDuration: Int,
    var hasWatched: Boolean,
    var note: Int,
    var genre: Genres
    ): Parcelable
