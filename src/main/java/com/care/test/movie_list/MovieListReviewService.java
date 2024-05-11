package com.care.test.movie_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieListReviewService {

    private final MovieListReviewRepository movieReviewRepository;

    @Autowired
    public MovieListReviewService(MovieListReviewRepository movieReviewRepository) {
        this.movieReviewRepository = movieReviewRepository;
    }

    public List<MovieListReview> getAllReviews() {
        return movieReviewRepository.findAll();
    }

    // 다른 필요한 메서드들도 추가할 수 있습니다.
}
