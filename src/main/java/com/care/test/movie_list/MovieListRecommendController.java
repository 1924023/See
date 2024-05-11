package com.care.test.movie_list;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieListRecommendController {

    @Autowired
    private MovieListInfoRepository movieListInfoRepository;

    @GetMapping("/randommovieinfoData") // 영화 랜덤 20개 추천
    public Map<String, List<Map<String, Object>>> getRandomMovieData() {
        // 모든 영화 정보를 데이터베이스에서 가져옴
        List<MovieListInfo> movies = movieListInfoRepository.findAll();
        // 랜덤하게 20개의 영화 선택
        List<MovieListInfo> randomMovies = getRandomElements(movies, 20);

        // 영화 정보를 Map으로 매핑하여 반환할 데이터 구성
        List<Map<String, Object>> movieList = randomMovies.stream()
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

    // 리스트에서 랜덤하게 n개의 요소를 선택하는 메서드
    private <T> List<T> getRandomElements(List<T> list, int n) {
        Collections.shuffle(list);
        return list.subList(0, Math.min(list.size(), n));
    }

    @GetMapping("/recommendedTopMovies")
    public Map<String, List<Map<String, Object>>> getRecommendedTopMovies() {  // 조회수 탑 20 추천
        // 조회수 기반으로 상위 20개 영화를 가져옴
        List<MovieListInfo> topMovies = movieListInfoRepository.findTop10ByOrderByViewcountDesc();

        // 영화 정보를 Map으로 매핑하여 반환할 데이터 구성
        List<Map<String, Object>> movieList = topMovies.stream()
                .map(movie -> {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("moviename", movie.getMoviename()); // 영화 이름
                    movieMap.put("thumbnail", movie.getMoviethumbnail()); // 썸네일 이미지
                    return movieMap;
                })
                .collect(Collectors.toList());

        Map<String, List<Map<String, Object>>> movieData = new HashMap<>();
        movieData.put("movies", movieList);

        return movieData; // 추천 영화 정보 반환
    }

}
