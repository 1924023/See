package com.care.test.movie_k;
import com.care.test.movie_ani.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// 애니메이션DB
public interface KMovieVideoRepository extends JpaRepository<KMovieVideo, Long> {
    Optional<KMovieVideo> findBymoviename(String moviename);
    Optional<KMovieVideo> findBymovievideo(byte[] movievideo);

    MovieInfo findByMoviename(String movieName);
}
