package com.example.lab10.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Movie implements Serializable {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("director")
    private String director;
    
    @SerializedName("duration")
    private Integer duration; // in minutes
    
    @SerializedName("genre")
    private String genre;
    
    @SerializedName("releaseDate")
    private String releaseDate;
    
    @SerializedName("posterUrl")
    private String posterUrl;
    
    @SerializedName("rating")
    private Double rating;
    
    @SerializedName("language")
    private String language;
    
    @SerializedName("ageRating")
    private String ageRating;

    // Constructors
    public Movie() {
    }

    public Movie(Long id, String title, String description, String director, 
                 Integer duration, String genre, String releaseDate, 
                 String posterUrl, Double rating, String language, String ageRating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.language = language;
        this.ageRating = ageRating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
}
