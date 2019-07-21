package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flickster.Movie;
import com.codepath.flickster.R;
import com.codepath.flickster.models.DetailActivity;

import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile", "onBindViewHolder: " + i);
        Movie movie = movies.get(i);
        // Bind the movie data into the ViewHolder
        viewHolder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
       // RecyclerView container;


        public ViewHolder(View itemView) {
            super( itemView );
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById( R.id.tvOverview );
            ivPoster = itemView.findViewById( R.id.ivPoster);
            //container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
            // Add click listener on the whole row
            //Navigate to datail activity on top
            ivPoster.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent( context, DetailActivity.class );
                    //i.putExtra( "title" , movie.getTitle());
                    i.putExtra( "movie", Parcels.wrap( movie ) );
                    context.startActivity( i );
                }
            } );

            tvOverview.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra( "movie", Parcels.wrap( movie ) );
                    context.startActivity( i );
                }
            } );

            tvTitle.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra( "movie", Parcels.wrap( movie ) );
                    context.startActivity( i );
                }
            } );
        }
    }
}
