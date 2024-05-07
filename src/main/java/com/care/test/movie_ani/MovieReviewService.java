package com.care.test.movie_ani;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;

    @Autowired
    public MovieReviewService(MovieReviewRepository movieReviewRepository) {
        this.movieReviewRepository = movieReviewRepository;
    }

    public List<MovieReview> getAllReviews() {
        return movieReviewRepository.findAll();
    }

    // 다른 필요한 메서드들도 추가할 수 있습니다.
}
