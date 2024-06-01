package com.care.test.movie_list;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreventMovieRepository extends JpaRepository<PreventMovie, Long> {
    List<PreventMovie> findAllByUserid(String userid);
    List<PreventMovie> findMovieTitlesByUserid(String userid);
    PreventMovie findByMoviename(String moviename);
    @Modifying
    @Transactional
    @Query("UPDATE PreventMovie s SET s.lastwatchedtime = :lastwatchedtime WHERE s.moviename = :moviename")
    void updateLastwatchedtime(@Param("lastwatchedtime") double lastwatchedtime,
                               @Param("moviename") String moviename);
}