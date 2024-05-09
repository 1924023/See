package com.care.test.movie_list;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListInfoRepository extends JpaRepository<MovieListInfo, Long> {
    MovieListInfo findByMoviename(String movieName);



    List<MovieListInfo> findByMoviedata(String 액션);
}
