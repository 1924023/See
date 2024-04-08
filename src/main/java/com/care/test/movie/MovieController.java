package com.care.test.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movie_list")

    public String index(){
        return "movie_list";
    }


    @GetMapping("/upload")
    public String uploadMovie(){
        return "upload_admin";
    }

    @PostMapping("/upload")
    public String uploadMovie(@ModelAttribute Movie movie,
                              @RequestParam("thumbnail") MultipartFile thumbnail,
                              @RequestParam("video") MultipartFile video,
                              @RequestParam("moviename") String moviename,
                              @RequestParam("actor") String actor,
                              @RequestParam("director") String director,
                              @RequestParam("genre") String genre) {
        try {
            movie.setMoviethumbnail(thumbnail.getBytes());
            movie.setMovievideo(video.getBytes());
            movie.setMoviename(moviename);
            movie.setMovieactor(actor);
            movie.setMoviedirector(director);
            movie.setMoviegenre(genre);
            // 데이터베이스에 저장
            movieRepository.save(movie);
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
}
