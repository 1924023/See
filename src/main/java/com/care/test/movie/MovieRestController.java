package com.care.test.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
public class MovieRestController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/moviesData")
    public Map<String, List<byte[]>> getMovieData() {
        List<Movie> movies = movieRepository.findAll();

        List<byte[]> thumbnails = movies.stream()
                .map(Movie::getMoviethumbnail)
                .collect(Collectors.toList());

        List<byte[]> videos = movies.stream()
                .map(Movie::getMovievideo)
                .collect(Collectors.toList());

        Map<String, List<byte[]>> movieData = new HashMap<>();
        movieData.put("thumbnails", thumbnails);
        movieData.put("videos", videos);

        return movieData;
    }
}
