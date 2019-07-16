package com.kens.moviedirectory.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kens.moviedirectory.Model.Movie;
import com.kens.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        movieList = movies;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating the view by using row

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_row, viewGroup, false);

        return new ViewHolder(view, context);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        //binding the view to data here

        Movie movie = movieList.get(i);
        String posterLink = movie.getPoster(); //retrieve the info on movie class

        viewHolder.title.setText(movie.getTitle());
        viewHolder.type.setText(movie.getMovieType());

        Picasso.with(context)
                .load(posterLink) // here we using the picasso to load the image
                .placeholder(android.R.drawable.ic_btn_speak_now) //fill it by an waiting image
                .into(viewHolder.poster); //inflate the proper image block

        viewHolder.year.setText(movie.getYear());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView poster;
        TextView year;
        TextView type;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.movieTitleId);
            poster = itemView.findViewById(R.id.movieImageId);
            year = itemView.findViewById(R.id.movieReleaseId);
            type = itemView.findViewById(R.id.movieCatId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Row Tapped", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }

}
