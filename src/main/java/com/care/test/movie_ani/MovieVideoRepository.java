package com.care.test.movie_ani;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// 애니메이션DB
public interface MovieVideoRepository extends JpaRepository<MovieVideo, Long> {
    Optional<MovieVideo> findBymoviename(String moviename);
    Optional<MovieVideo> findBymovievideo(byte[] movievideo);
}
