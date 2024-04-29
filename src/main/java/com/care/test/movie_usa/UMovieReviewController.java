package com.care.test.movie_usa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/umoviereview")
public class UMovieReviewController {
    @Autowired
    private UMovieReviewRepository movieReviewRepository;

    // 새로운 리뷰 추가
    @PostMapping
    public ResponseEntity<UMovieReview> addReview(@RequestBody UMovieReview review) {
        review.setLoginid("익명"); // 사용자 ID를 익명으로 설정
        movieReviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review); // HTTP 상태 코드 201과 함께 새로운 리뷰 반환
    }
}
