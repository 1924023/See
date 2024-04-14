package com.care.test.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovieVideoStreamingController {

    @Autowired
    private MovieVideoRepository movieVideoRepository;

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @GetMapping("/movieinfoData")
    public Map<String, List<Map<String, Object>>> getMovieData() {
        List<MovieInfo> movies = movieInfoRepository.findAll();

        List<Map<String, Object>> movieList = movies.stream()
                .map(movie -> {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("moviename", movie.getMoviename());
                    movieMap.put("thumbnail", movie.getMoviethumbnail());
                    return movieMap;
                })
                .collect(Collectors.toList());

        Map<String, List<Map<String, Object>>> movieData = new HashMap<>();
        movieData.put("movies", movieList);

        return movieData;
    }

    @GetMapping("/streamingEndpoint")
    public ResponseEntity<StreamingResponseBody> streamVideoByMovieName(
            @RequestParam String movieName,
            @RequestParam(defaultValue = "0") double currentTime) {

        Optional<MovieVideo> movieOptional = movieVideoRepository.findBymoviename(movieName);

        if (movieOptional.isPresent()) {
            MovieVideo movieVideo = movieOptional.get();
            byte[] videoBytes = movieVideo.getMovievideo();
            long videoLength = videoBytes.length;

            long rangeStart = (long) (currentTime * videoLength);
            long rangeEnd = videoLength - 1;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4"));
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + videoLength);

            StreamingResponseBody responseBody = outputStream -> {
                try {
                    outputStream.write(videoBytes, (int) rangeStart, (int) (rangeEnd - rangeStart + 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(responseBody);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
