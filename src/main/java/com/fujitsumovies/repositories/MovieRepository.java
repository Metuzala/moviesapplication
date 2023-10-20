package com.fujitsumovies.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fujitsumovies.models.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;

@Repository
public class MovieRepository {
    private final ObjectMapper objectMapper;
    private final Yaml yaml;
    private final File jsonFile;
    private final File yamlFile;

    public MovieRepository(@Value("${movie.json.filepath}") String jsonFilePath, @Value("${movie.yaml.filepath}") String yamlFilePath) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.yaml = new Yaml();
        this.jsonFile = new File(jsonFilePath);
        this.yamlFile = new File(yamlFilePath);
    }

    public List<Movie> getAllMoviesFromJSON() throws IOException {
        if (!jsonFile.exists()) {
            throw new IOException("JSON file does not exist.");
        }
        return objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
    }

    public List<Movie> getAllMoviesFromYAML() throws IOException {
        if (!yamlFile.exists()) {
            throw new IOException("YAML file does not exist.");
        }
        try (FileInputStream fis = new FileInputStream(yamlFile)) {
            Yaml yaml = new Yaml();
            return yaml.load(fis);
        }
    }

    public void saveMoviesToJSON(List<Movie> movies) throws IOException {
        objectMapper.writeValue(jsonFile, movies);
    }

    public void saveMoviesToYAML(List<Movie> movies) throws IOException {
        try (Writer writer = new FileWriter(yamlFile)) {
            yaml.dump(movies, writer);
        }
    }
}
