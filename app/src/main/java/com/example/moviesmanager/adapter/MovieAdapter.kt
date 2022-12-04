package com.example.moviesmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesmanager.R
import com.example.moviesmanager.model.Movie

class MovieAdapter(private val listMovies: MutableList<Movie>, internal var ctx: Context) :


    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    var mOnItemClickListener: OnItemClickListener? = null
    interface  OnItemClickListener {
        fun onItemClick(index: Int, movie: Movie)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView
        val yearTv: TextView
        val studioTv: TextView
        val timeOfDurationTv: TextView
        val watchedTv: TextView
        val noteTv: TextView
        val genreTv: TextView

        init {
            nameTv = view.findViewById(R.id.nameTv)
            yearTv = view.findViewById(R.id.yearTv)
            studioTv = view.findViewById(R.id.studioTv)
            timeOfDurationTv = view.findViewById(R.id.timeOfDurationTv)
            watchedTv = view.findViewById(R.id.watchedTv)
            noteTv = view.findViewById(R.id.noteTv)
            genreTv = view.findViewById(R.id.genreTv)

        }

    }



    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(ctx)
            .inflate(R.layout.tile_movie, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = listMovies.get(position)

        viewHolder.itemView.setOnClickListener{
            mOnItemClickListener?.onItemClick(position, movie)

        }

        viewHolder.nameTv.text = movie.name
        viewHolder.yearTv.text = "Year :  ${movie.year}"
        viewHolder.studioTv.text = "Studio : ${movie.studio}"
        viewHolder.timeOfDurationTv.text = "Time of Duration: ${movie.timeOfDuration}"
        val textWatched = if(movie.hasWatched) "Yes " else "No"
        viewHolder.watchedTv.text = "Watched: $textWatched"
        viewHolder.noteTv.text = "Note: ${movie.note}"
        viewHolder.genreTv.text = "Genre: ${movie.genre}"



    }







    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listMovies.size }