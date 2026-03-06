package com.example.lab10.api;

import com.example.lab10.models.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface MovieApiService {

    // Movie endpoints
    @GET("api/movies")
    Call<List<Movie>> getAllMovies();

    @GET("api/movies/{id}")
    Call<Movie> getMovieById(@Path("id") Long id);

    @GET("api/movies/detail/{id}")
    Call<ApiResponse<Movie>> getMovieDetailById(@Path("id") Long id);

//    @GET("api/movies/upcomingMovies")
//    Call<ApiResponse<MovieSearchResponse>> getUpcomingMovies();

    @GET("api/movies/showtimes")
    Call<List<Movie>> getMoviesWithShowtimes();

//    @GET("api/movies/search")
//    Call<ApiResponse<MovieSearchResponse>> searchMovies(
//        @Query("keyword") String keyword,
//        @Query("type") String type,
//        @Query("page") int page,
//        @Query("size") int size
//    );

    @GET("api/movies/now-showing")
    Call<List<Movie>> getNowShowingMovies();

    @GET("api/movies/coming-soon")
    Call<List<Movie>> getComingSoonMovies();

}
