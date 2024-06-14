package com.care.test.movie_list;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListReviewRepository extends JpaRepository<MovieListReview, Long> {

    List<MovieListReview> findByMovieName(String movieName);
}
