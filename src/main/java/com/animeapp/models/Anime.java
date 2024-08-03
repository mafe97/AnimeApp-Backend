package com.animeapp.models;

public class Anime {
    private Long id;
    private String title;
    private Double score;
    private String season;

    public Anime(Long id, String title, Double score, String season) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.season = season;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }
}