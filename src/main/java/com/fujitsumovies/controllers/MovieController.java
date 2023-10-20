package com.fujitsumovies.controllers;

import com.fujitsumovies.models.Movie;
import com.fujitsumovies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = {"application/json", "application/yaml" })
    public ResponseEntity<?> getMovies(@RequestParam("format") String format) {
        try {
            List<Movie> movies = movieService.getAllMovies(format);
            return ResponseEntity.ok(movies);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while fetching movies.");
        }
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie, @RequestParam("format") String format) {
        try {
            movieService.addMovie(movie, format);
            return ResponseEntity.ok("Movie added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while adding the movie.");
        }
    }

    @DeleteMapping("{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId, @RequestParam String format) {
        try {
            movieService.deleteMovie(movieId, format);
            return ResponseEntity.status(200).body("Movie deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting movie");
        }
    }
}
