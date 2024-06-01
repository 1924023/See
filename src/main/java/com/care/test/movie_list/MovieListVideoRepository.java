package com.care.test.movie_list;
import com.care.test.movie_list.MovieListVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MovieListVideoRepository extends JpaRepository<MovieListVideo, Long> {
    Optional<MovieListVideo> findBymoviename(String moviename);
    Optional<MovieListVideo> findBymovievideo(byte[] movievideo);

    MovieListInfo findByMoviename(String movieName);
}