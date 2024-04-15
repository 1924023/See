package com.care.test.movie_k;

import com.care.test.movie_ani.MovieInfo;
import com.care.test.movie_ani.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class KMovieVideoStreamingController { // 국내영화


    @Autowired
    private KMovieVideoRepository KMovieVideoRepository;

    @Autowired
    private KMovieInfoRepository kmovieInfoRepository;

    // 영화 정보를 반환하는 엔드포인트
    @GetMapping("/kmovieinfoData")
    public Map<String, List<Map<String, Object>>> getMovieData() {
        // 모든 영화 정보를 데이터베이스에서 가져옴
        List<KMovieInfo> movies = kmovieInfoRepository.findAll();

        // 영화 정보를 Map으로 매핑하여 반환할 데이터 구성
        List<Map<String, Object>> movieList = movies.stream()
                .map(movie -> {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("moviename", movie.getMoviename()); // 영화 이름
                    movieMap.put("thumbnail", movie.getMoviethumbnail()); // 썸네일 이미지
                    return movieMap;
                })
                .collect(Collectors.toList());

        Map<String, List<Map<String, Object>>> movieData = new HashMap<>();
        movieData.put("movies", movieList);

        return movieData; // 영화 정보 반환
    }

    // 영상을 스트리밍하는 엔드포인트
    @GetMapping("/kstreamingEndpoint")
    public ResponseEntity<StreamingResponseBody> streamVideoByMovieName(
            @RequestParam String movieName,
            @RequestParam(defaultValue = "0") double currentTime) {

        // 요청된 영화 이름으로 해당하는 영상 정보를 데이터베이스에서 찾음
        Optional<KMovieVideo> movieOptional = KMovieVideoRepository.findBymoviename(movieName);

        if (movieOptional.isPresent()) {
            KMovieVideo KMovieVideo = movieOptional.get();
            byte[] videoBytes = KMovieVideo.getMovievideo(); // 영상의 바이트 배열
            long videoLength = videoBytes.length; // 영상의 전체 길이

            // 요청된 시간에 따라 스트리밍할 영상의 범위를 설정
            long rangeStart = (long) (currentTime * videoLength); // 스트리밍 시작 위치
            long rangeEnd = videoLength - 1; // 스트리밍 종료 위치

            // HTTP 응답 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4")); // 컨텐츠 타입 설정
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + videoLength); // 컨텐츠 범위 설정

            // 스트리밍을 위한 StreamingResponseBody 생성
            StreamingResponseBody responseBody = outputStream -> {
                try {
                    // 영상의 바이트 배열에서 지정된 범위의 데이터를 출력 스트림으로 전송
                    outputStream.write(videoBytes, (int) rangeStart, (int) (rangeEnd - rangeStart + 1));
                } catch (IOException e) {
                    // IOException이 발생한 경우 예외 처리
                    e.printStackTrace();
                }
            };

            // 부분 컨텐츠 응답 반환
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(responseBody);
        } else {
            // 요청된 영화 이름에 해당하는 영상이 없는 경우 404 응답 반환
            return ResponseEntity.notFound().build();
        }
    }
}
