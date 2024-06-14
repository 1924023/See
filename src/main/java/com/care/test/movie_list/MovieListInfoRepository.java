package com.care.test.movie_list;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface MovieListInfoRepository extends JpaRepository<MovieListInfo, Long> {
    MovieListInfo findByMoviename(String movieName);



    List<MovieListInfo> findByMoviedata(String 액션);


    List<MovieListInfo> findTop10ByOrderByViewcountDesc();
    List<MovieListInfo> findTop10ByMoviedataOrderByViewcountDesc(String 액션);

    List<MovieListInfo> findMoviedataByMovienameIn(List<String> moviename);
    List<MovieListInfo> findMovienameByMoviedataIn(List<String> moviedata);
    List<MovieListInfo> findByMovienameContainingIgnoreCaseOrMovieactorContainingIgnoreCaseOrMoviedirectorContainingIgnoreCaseOrMoviegenreContainingIgnoreCase(String name, String actor, String director, String genre);
}