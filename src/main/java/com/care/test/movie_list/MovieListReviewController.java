package com.care.test.movie_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moviereview")
public class MovieListReviewController {
    @Autowired
    private MovieListReviewRepository movieReviewRepository;


    // 새로운 리뷰 추가
    @PostMapping
    public ResponseEntity<MovieListReview> addReview(@RequestBody MovieListReview review) {
        review.setLoginid("익명"); // 사용자 ID를 익명으로 설정
        movieReviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review); // HTTP 상태 코드 201과 함께 새로운 리뷰 반환
    }

    // 특정 영화에 대한 리뷰 가져오기
    @GetMapping
    public ResponseEntity<List<MovieListReview>> getReviewsForMovie(@RequestParam("movieName") String movieName) {
        // 해당 영화에 대한 리뷰를 데이터베이스에서 가져오기
        List<MovieListReview> reviews = movieReviewRepository.findByMovieName(movieName);
        return ResponseEntity.ok().body(reviews);
    }
}
