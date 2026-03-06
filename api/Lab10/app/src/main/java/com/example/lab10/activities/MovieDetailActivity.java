package com.example.lab10.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10.R;
import com.example.lab10.api.ApiClient;
import com.example.lab10.api.MovieApiService;
import com.example.lab10.models.Movie;
import com.example.lab10.utils.ImageLoader;

public class MovieDetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_MOVIE = "extra_movie";
    
    private ImageView ivPoster, ivBack;
    private TextView tvTitle, tvGenre, tvDuration, tvRating, tvDirector, tvLanguage, tvAgeRating, tvDescription;
    private ProgressBar progressBar;
    
    private Movie movie;
    private MovieApiService apiService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        
        apiService = ApiClient.getApiService();
        
        movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (movie == null) {
            Toast.makeText(this, "Movie data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initViews();
        displayMovieDetails();

    }
    
    private void initViews() {
        ivPoster = findViewById(R.id.iv_poster);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvDuration = findViewById(R.id.tv_duration);
        tvRating = findViewById(R.id.tv_rating);
        tvDirector = findViewById(R.id.tv_director);
        tvLanguage = findViewById(R.id.tv_language);
        tvAgeRating = findViewById(R.id.tv_age_rating);
        tvDescription = findViewById(R.id.tv_description);
        progressBar = findViewById(R.id.progress_bar);
        
        ivBack.setOnClickListener(v -> finish());
        

    }
    
    private void displayMovieDetails() {
        ImageLoader.loadImageWithPlaceholder(ivPoster, movie.getPosterUrl(), R.drawable.ic_launcher_foreground);
        tvTitle.setText(movie.getTitle());
        tvGenre.setText(movie.getGenre());
        tvDuration.setText(movie.getDuration() + " phút");
        tvRating.setText(String.valueOf(movie.getRating()));
        tvDirector.setText("Đạo diễn: " + movie.getDirector());
        tvLanguage.setText("Ngôn ngữ: " + movie.getLanguage());
        tvAgeRating.setText(movie.getAgeRating());
        tvDescription.setText(movie.getDescription());
    }
    

    

}
