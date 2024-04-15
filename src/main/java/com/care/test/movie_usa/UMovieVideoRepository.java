package com.care.test.movie_usa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// 애니메이션DB
public interface UMovieVideoRepository extends JpaRepository<UMovieVideo, Long> {
    Optional<UMovieVideo> findBymoviename(String moviename);
    Optional<UMovieVideo> findBymovievideo(byte[] movievideo);
}
