package com.care.test.movie_usa;

import com.care.test.movie_k.KMovieReview;
import com.care.test.movie_k.KMovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UMovieReviewService {

    private final UMovieReviewRepository uMovieReviewRepository;

    @Autowired
    public UMovieReviewService(UMovieReviewRepository uMovieReviewRepository) {
        this.uMovieReviewRepository = uMovieReviewRepository;
    }

    public List<UMovieReview> getAllReviews() {
        return uMovieReviewRepository.findAll();
    }

    // 다른 필요한 메서드들도 추가할 수 있습니다.
}
