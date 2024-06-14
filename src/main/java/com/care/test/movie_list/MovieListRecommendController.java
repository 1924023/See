package com.care.test.movie_list;

import java.util.*;
import java.util.stream.Collectors;

import com.care.test.member.UserRepository;
import com.care.test.pay.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.util.WebUtils.getSessionId;

@RestController
public class MovieListRecommendController {


    private final MovieListInfoRepository movieListInfoRepository;
    private final PreventMovieRepository preventMovieRepository;

    @Autowired
    public MovieListRecommendController(MovieListInfoRepository movieListInfoRepository, PreventMovieRepository preventMovieRepository) {
        this.movieListInfoRepository = movieListInfoRepository;
        this.preventMovieRepository = preventMovieRepository;
    }


    @GetMapping("/recommendedTopMovies")
    public Map<String, List<Map<String, Object>>> getRecommendedTopMovies() {  // 조회수 탑 20 추천
        // 조회수 기반으로 상위 20개 영화를 가져옴
        List<MovieListInfo> topMovies = movieListInfoRepository.findTop10ByOrderByViewcountDesc();
        System.out.println(topMovies);

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

    @GetMapping("/recommendedActionTopMovies")
    public Map<String, List<Map<String, Object>>> getRecommendedActionTopMovies() {  // 액션 탑 10 추천
        // 조회수 기반으로 상위 20개 영화를 가져옴
        List<MovieListInfo> topMovies = movieListInfoRepository.findTop10ByMoviedataOrderByViewcountDesc("액션");
        System.out.println(topMovies);

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

    @GetMapping("/recommendedRoTopMovies")
    public Map<String, List<Map<String, Object>>> getRecommendedRoTopMovies() {  // 로맨스 탑 10 추천
        // 조회수 기반으로 상위 20개 영화를 가져옴
        List<MovieListInfo> topMovies = movieListInfoRepository.findTop10ByMoviedataOrderByViewcountDesc("로맨스");
        System.out.println(topMovies);

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

    @GetMapping("/recommendedAniTopMovies")
    public Map<String, List<Map<String, Object>>> getRecommendedAniTopMovies() {  // 애니 탑 10 추천
        // 조회수 기반으로 상위 20개 영화를 가져옴
        List<MovieListInfo> topMovies = movieListInfoRepository.findTop10ByMoviedataOrderByViewcountDesc("애니메이션");
        System.out.println(topMovies);

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

    @GetMapping("/preventmovieinfoData")
    public Map<String, List<Map<String, Object>>> getPreventmovieinfoData(HttpSession session) {
        // 사용자의 세션 ID 가져오기
        String sessionId = (String)session.getAttribute("login_success_id");
        System.out.println(sessionId);
        // 사용자가 시청한 영화 제목들 조회
        List<PreventMovie> watchedMovieTitles = preventMovieRepository.findMovieTitlesByUserid(sessionId);
        ArrayList<String> movieTitles = new ArrayList<>();
        for(PreventMovie  prevent : watchedMovieTitles){
            movieTitles.add(prevent.getMoviename());
            System.out.println("prevent 영화 제목 : " + prevent.getMoviename());
        }
        // 사용자가 시청한 영화들의 장르 가져오기
        List<MovieListInfo> genres = movieListInfoRepository.findMoviedataByMovienameIn(movieTitles);
        ArrayList<String> movieGerne = new ArrayList<>();
        for(MovieListInfo mli : genres){
            movieGerne.add(mli.getMoviedata());
            System.out.println("movielistinfo 영화 장르 : " + mli.getMoviedata());
        }
        // 장르에 맞는 다른 영화들 가져오기
        List<MovieListInfo> recommendedMovies = movieListInfoRepository.findMovienameByMoviedataIn(movieGerne);

        System.out.println(recommendedMovies);
        // 영화를 랜덤하게 섞음
        Collections.shuffle(recommendedMovies);

        // 추천할 영화 개수 설정
        int numberOfMoviesToShow = 5;

        // 추천 영화 목록에서 원하는 개수만큼 선택
        List<MovieListInfo> selectedMovies = recommendedMovies.subList(0, Math.min(numberOfMoviesToShow, recommendedMovies.size()));

        // 최종 영화 정보를 Map으로 매핑하여 반환할 데이터 구성
        List<Map<String, Object>> movieList = selectedMovies.stream()
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