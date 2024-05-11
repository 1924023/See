package com.care.test.movie_list;

import com.care.test.admin.AdminRepository;
import com.care.test.member.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PreventMovieController {

    private final PreventMovieRepository preventMovieRepository;
    private final MovieListInfoRepository movieListInfoRepository;

    @Autowired
    public PreventMovieController(MovieListInfoRepository movieListInfoRepository, PreventMovieRepository preventMovieRepository) {
        this.movieListInfoRepository = movieListInfoRepository;
        this.preventMovieRepository = preventMovieRepository;
    }

    @PostMapping("/preventmovie")
    public ResponseEntity<String> savePreventMovieData(@ModelAttribute PreventMovie preventMovie) {
        // 클라이언트로부터 받은 데이터를 데이터베이스에 저장합니다.
        PreventMovie savedMovie = preventMovieRepository.save(preventMovie);

        if (savedMovie != null) {
            // 저장에 성공한 경우 200 OK 응답을 반환합니다.
            return ResponseEntity.ok("PreventMovie data successfully saved.");
        } else {
            // 저장에 실패한 경우 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save PreventMovie data.");
        }
    }

    @GetMapping("/preventmovie")
    public ResponseEntity<List<String>> getMovieNamesByUserId(@RequestParam("userid") String userid) {
        // 사용자 ID를 기반으로 prevent_movie 테이블에서 해당 사용자의 영화 제목들을 가져옵니다.
        List<PreventMovie> movies = preventMovieRepository.findAllByUserid(userid);
        System.out.println("userid : " + userid);
        List<String> movieNames = new ArrayList<>();
        if (!movies.isEmpty()) {
            // 모든 영화의 제목을 리스트에 추가합니다.
            for (PreventMovie movie : movies) {
                movieNames.add(movie.getMoviename());
            }
            return ResponseEntity.ok(movieNames);
        } else {
            // 해당 사용자의 시청 중인 영화가 없을 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PostMapping("/watchingmovieinfoData")
    public ResponseEntity<List<Map<String, Object>>> getMovieData(@RequestBody List<String> movieNames) {
        // movieNames와 일치하는 영화의 썸네일만 가져오기
        System.out.println(movieNames);
        List<Map<String, Object>> matchedMovies = new ArrayList<>();
        for (String movieName : movieNames) {
            // movieName에 해당하는 영화 정보 조회
            MovieListInfo movie = movieListInfoRepository.findByMoviename(movieName);
            if (movie != null) {
                // 영화 정보를 Map으로 변환하여 리스트에 추가
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("moviename", movie.getMoviename()); // 영화 이름
                movieMap.put("thumbnail", movie.getMoviethumbnail()); // 썸네일 이미지
                matchedMovies.add(movieMap);
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(matchedMovies); // 영화 정보 반환
    }

}