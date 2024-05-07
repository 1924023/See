package com.care.test.movie_k;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KMovieReviewRepository extends JpaRepository<KMovieReview, Long> {
    List<KMovieReview> findByMovieName(String movieName);
}
