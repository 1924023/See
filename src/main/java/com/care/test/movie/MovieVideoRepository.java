package com.care.test.movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieVideoRepository extends JpaRepository<MovieVideo, Long> {
    Optional<MovieVideo> findBymoviename(String moviename);
    Optional<MovieVideo> findBymovievideo(byte[] movievideo);
}
