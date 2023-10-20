package com.fujitsumovies.services;

import com.fujitsumovies.models.Movie;
import com.fujitsumovies.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(String format) throws IOException {
        if ("json".equalsIgnoreCase(format)) {
            return movieRepository.getAllMoviesFromJSON();
        } else if ("yaml".equalsIgnoreCase(format)) {
            return movieRepository.getAllMoviesFromYAML();
        } else {
            throw new IllegalArgumentException("Invalid format specified. Use 'json' or 'yaml'.");
        }
    }

    public void addMovie(Movie movie, String format) throws IOException {
        List<Movie> movies = getAllMovies("json");
        movies.add(movie);

        if ("json".equalsIgnoreCase(format)) {
            movieRepository.saveMoviesToJSON(movies);
        } else if ("yaml".equalsIgnoreCase(format)) {
            movieRepository.saveMoviesToYAML(movies);
        } else {
            throw new IllegalArgumentException("Invalid format specified. Use 'json' or 'yaml'.");
        }
    }

    public void deleteMovie(Long movieId, String format) throws IOException {
        List<Movie> movies = getAllMovies(format);

        Movie movieToDelete = movies.stream()
                .filter(movie -> Long.valueOf(movie.getId()).equals(movieId))
                .findFirst()
                .orElse(null);

        if (movieToDelete == null) {
            throw new IOException("Movie not found");
        }

        movies.remove(movieToDelete);

        if ("json".equalsIgnoreCase(format)) {
            movieRepository.saveMoviesToJSON(movies);
        } else if ("yaml".equalsIgnoreCase(format)) {
            movieRepository.saveMoviesToYAML(movies);
        }
    }
}
