package com.care.test.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.*;
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
    public ResponseEntity<Flux<ResourceRegion>> streamVideoByMovieName(@RequestParam String movieName,
                                                                       @RequestParam(required = false) Long rangeStart,
                                                                       @RequestParam(required = false) Long rangeEnd) {
        // 영화 이름으로 해당하는 영상을 찾는다.
        Optional<MovieVideo> movieOptional = movieVideoRepository.findBymoviename(movieName);

        if (movieOptional.isPresent()) {
            MovieVideo movieVideo = movieOptional.get();

            // 영상을 스트리밍하는 데 사용할 수 있는 Flux를 생성한다.
            Flux<ResourceRegion> videoStream = createVideoStream(movieVideo, rangeStart, rangeEnd);

            // 영상을 스트리밍하는 데 사용할 수 있는 MediaType을 지정한다.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4"));

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(videoStream);
        } else {
            // 해당하는 영상이 없는 경우 404 에러를 반환한다.
            return ResponseEntity.notFound().build();
        }
    }

    // 비디오를 스트리밍하는 메서드 (ResourceRegion 사용)
    private Flux<ResourceRegion> createVideoStream(MovieVideo movieVideo, Long rangeStart, Long rangeEnd) {
        byte[] videoBytes = movieVideo.getMovievideo();
        long contentLength = videoBytes.length;

        // 영상의 범위 설정
        long chunkSize = 10 * 1000 * 1000; // 10초 단위
        long start = rangeStart != null ? rangeStart : 0;
        long end = rangeEnd != null ? rangeEnd : contentLength - 1;
        long rangeLength = end - start + 1;

        // ResourceRegion을 생성하여 스트리밍한다.
        List<ResourceRegion> regions = new ArrayList<>();
        for (long i = start; i <= end; i += chunkSize) {
            long chunkStart = i;
            long chunkEnd = Math.min(i + chunkSize - 1, end);
            long chunkLength = chunkEnd - chunkStart + 1;
            ByteArrayResource resource = new ByteArrayResource(Arrays.copyOfRange(videoBytes, (int) chunkStart, (int) chunkEnd + 1));
            regions.add(new ResourceRegion(resource, chunkStart, chunkLength));
        }

        return Flux.fromIterable(regions);
    }
}