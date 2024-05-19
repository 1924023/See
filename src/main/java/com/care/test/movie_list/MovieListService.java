package com.care.test.movie_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieListService {

    @Autowired
    private MovieListInfoRepository movieListInfoRepository;



    // 영화 조회수 증가 메서드
    public boolean increaseViewCount(String movieName) {
        // 영화 조회수를 증가시키는 로직 구현
        MovieListInfo movieListInfo = movieListInfoRepository.findByMoviename(movieName);
        if (movieListInfo != null) {
            movieListInfo.setViewcount(movieListInfo.getViewcount() + 1); // 조회수 증가
            movieListInfoRepository.save(movieListInfo); // 변경된 정보 저장
            return true;
        } else {
            return false;
        }
    }
}

