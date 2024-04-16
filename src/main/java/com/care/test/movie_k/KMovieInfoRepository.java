package com.care.test.movie_k;
import org.springframework.data.jpa.repository.JpaRepository;
// 국내영화 DB
public interface KMovieInfoRepository extends JpaRepository<KMovieInfo, Long> {
    KMovieInfo findByMoviename(String movieName);
}
