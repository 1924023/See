package com.care.test.movie_usa;
import org.springframework.data.jpa.repository.JpaRepository;
// 애니메이션 DB
public interface UMovieInfoRepository extends JpaRepository<UMovieInfo, Long> {
}
