package com.fujitsumovies.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Movie {
    public Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String category;
    private List<String> actors;
    private String description;

    private final AtomicLong idCounter = new AtomicLong(1);

    public Movie() {
        super();
        this.id = generateNextId();
    }

    public Movie(String title, Date releaseDate, String category, List<String> actors, String description) {
        this();
        this.title = title;
        this.releaseDate = releaseDate;
        this.category = category;
        this.actors = actors;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    private long generateNextId() {
        return idCounter.getAndIncrement();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

