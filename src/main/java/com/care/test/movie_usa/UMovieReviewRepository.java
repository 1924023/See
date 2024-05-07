package com.care.test.movie_usa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UMovieReviewRepository extends JpaRepository<UMovieReview, Long> {
    List<UMovieReview> findByMovieName(String movieName);
}
