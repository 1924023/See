package com.care.test.movie_list;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieListInfoController {

    @Autowired
    private MovieListInfoRepository movieListInfoRepository;

    @Autowired
    private MovieListService movieListService; // MovieService 주입

    @GetMapping("/movie_list")
    public String movie_list() {
        return "movie_list";
    }
    @GetMapping("/movie_recommend")
    public String movie_recommend() {
        return "movie_recommend";
    }

    @GetMapping("/allmovieinfoupload")
    public String uploadMovie() {
        return "allmovieinfo_upload";
    }

    @PostMapping("/allmovieinfoupload")
    public String uploadMovie(@ModelAttribute MovieListInfo movieListInfo,
                              @RequestParam("thumbnail") MultipartFile thumbnail,
                              @RequestParam("moviename") String moviename,
                              @RequestParam("actor") String actor,
                              @RequestParam("director") String director,
                              @RequestParam("genre") String genre,
                              @RequestParam("data") String data) {
        try {
            movieListInfo.setMoviethumbnail(thumbnail.getBytes());
            movieListInfo.setMoviename(moviename);
            movieListInfo.setMovieactor(actor);
            movieListInfo.setMoviedirector(director);
            movieListInfo.setMoviegenre(genre);
            movieListInfo.setMoviedata(data);
            // 데이터베이스에 저장
            movieListInfoRepository.save(movieListInfo);
            // 업로드 성공 메시지 출력
            System.out.println("영화 업로드가 성공했습니다.");
            // 성공 페이지로 리다이렉트
            return "redirect:/success";
        } catch (Exception e) {
            // 업로드 실패 메시지 출력
            System.out.println("영화 업로드에 실패했습니다: " + e.getMessage());
            // 실패 페이지로 리다이렉트
            return "redirect:/error";
        }
    }

    @PostMapping("/api/increaseViewCount")
    @ResponseBody
    public String increaseViewCount(@RequestParam("movieName") String movieName) {
        // 해당 영화의 조회수 증가
        boolean success = movieListService.increaseViewCount(movieName);
        if (success) {
            return "조회수 증가 성공";
        } else {
            return "조회수 증가 실패";
        }
    }
}
