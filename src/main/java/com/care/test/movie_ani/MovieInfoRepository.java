package com.care.test.movie_ani;
import org.springframework.data.jpa.repository.JpaRepository;
// 애니메이션 DB
public interface MovieInfoRepository extends JpaRepository<MovieInfo, Long> {
}
