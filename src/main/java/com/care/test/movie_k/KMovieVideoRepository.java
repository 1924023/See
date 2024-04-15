package com.care.test.movie_k;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// 애니메이션DB
public interface KMovieVideoRepository extends JpaRepository<KMovieVideo, Long> {
    Optional<KMovieVideo> findBymoviename(String moviename);
    Optional<KMovieVideo> findBymovievideo(byte[] movievideo);
}
