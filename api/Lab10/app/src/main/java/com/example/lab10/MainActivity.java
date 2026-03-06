package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10.activities.MovieDetailActivity;
import com.example.lab10.adapters.MovieAdapter;
import com.example.lab10.api.ApiClient;
import com.example.lab10.api.MovieApiService;
import com.example.lab10.models.ApiResponse;
import com.example.lab10.models.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private MovieAdapter movieAdapter;
    private MovieApiService apiService;
    private final List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getApiService();
        
        initViews();
        loadMoviesByIds();
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        rvMovies = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.progress_bar);
        tvEmpty = findViewById(R.id.tv_empty);
        
        if (rvMovies != null) {
            rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
            movieAdapter = new MovieAdapter(new ArrayList<>(), this::onMovieClick);
            rvMovies.setAdapter(movieAdapter);
        }
    }
    
    private void loadMoviesByIds() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (tvEmpty != null) tvEmpty.setVisibility(View.GONE);
        
        movieList.clear();
        AtomicInteger completedRequests = new AtomicInteger(0);
        int totalRequests = 10;

        for (long i = 1; i <= totalRequests; i++) {
            apiService.getMovieDetailById(i).enqueue(new Callback<ApiResponse<Movie>>() {
                @Override
                public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        synchronized (movieList) {
                            movieList.add(response.body().getData());
                        }
                    }
                    checkCompletion(completedRequests.incrementAndGet(), totalRequests);
                }

                @Override
                public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
                    checkCompletion(completedRequests.incrementAndGet(), totalRequests);
                }
            });
        }
    }

    private void checkCompletion(int completed, int total) {
        if (completed == total) {
            runOnUiThread(() -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (movieList.isEmpty()) {
                    if (tvEmpty != null) tvEmpty.setVisibility(View.VISIBLE);
                    loadAllMoviesFallback();
                } else {
                    Collections.sort(movieList, (m1, m2) -> {
                        if (m1.getId() == null || m2.getId() == null) return 0;
                        return m1.getId().compareTo(m2.getId());
                    });
                    movieAdapter.updateData(movieList);
                }
            });
        }
    }

    private void loadAllMoviesFallback() {
        apiService.getAllMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieAdapter.updateData(response.body());
                    if (tvEmpty != null) tvEmpty.setVisibility(response.body().isEmpty() ? View.VISIBLE : View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {}
        });
    }
    
    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
    


}