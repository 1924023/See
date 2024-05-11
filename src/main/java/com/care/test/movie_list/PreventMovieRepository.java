package com.care.test.movie_list;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreventMovieRepository extends JpaRepository<PreventMovie, Long> {
    List<PreventMovie> findAllByUserid(String userid);
}