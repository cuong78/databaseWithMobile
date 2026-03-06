package com.example.lab10.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10.R;
import com.example.lab10.models.Movie;
import com.example.lab10.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    
    private List<Movie> movies;
    private OnMovieClickListener listener;
    
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
    
    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies != null ? movies : new ArrayList<>();
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie, listener);
    }
    
    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }
    
    public void updateData(List<Movie> newMovies) {
        this.movies = newMovies != null ? newMovies : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivPoster;
        private final TextView tvTitle;
        private final TextView tvGenre;
        private final TextView tvRating;
        private final TextView tvDuration;
        private final TextView tvLanguage;
        
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvLanguage = itemView.findViewById(R.id.tv_language);
        }
        
        public void bind(Movie movie, OnMovieClickListener listener) {
            if (movie == null) return;

            if (ivPoster != null) {
                ImageLoader.loadImageWithPlaceholder(ivPoster, movie.getPosterUrl(), 
                        R.drawable.ic_launcher_foreground);
            }
            
            if (tvTitle != null) {
                tvTitle.setText(movie.getTitle() != null ? movie.getTitle() : "No Title");
            }
            
            if (tvGenre != null) {
                tvGenre.setText(movie.getGenre() != null ? movie.getGenre() : "");
            }
            
            if (tvRating != null) {
                Double rating = movie.getRating();
                tvRating.setText("⭐ " + (rating != null ? rating : "0.0"));
            }

            if (tvDuration != null) {
                Integer duration = movie.getDuration();
                tvDuration.setText(duration != null ? duration + " min" : "");
            }

            if (tvLanguage != null) {
                tvLanguage.setText(movie.getLanguage() != null ? movie.getLanguage() : "");
            }
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMovieClick(movie);
                }
            });
        }
    }
}
