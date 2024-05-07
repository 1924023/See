package com.care.test.movie_k;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KMovieReviewService {

    private final KMovieReviewRepository kMovieReviewRepository;

    @Autowired
    public KMovieReviewService(KMovieReviewRepository kMovieReviewRepository) {
        this.kMovieReviewRepository = kMovieReviewRepository;
    }

    public List<KMovieReview> getAllReviews() {
        return kMovieReviewRepository.findAll();
    }

    // 다른 필요한 메서드들도 추가할 수 있습니다.
}
