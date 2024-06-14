package com.care.test.movie_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieListVideoUploadController {

    @Autowired
    private MovieListVideoRepository movieListVideoRepository;




    @GetMapping("/allmovievideoupload")
    public String uploadMovie(){
        return "allmovie_upload";
    }

    @PostMapping("/allmovievideoupload")
    public String uploadMovie(@ModelAttribute MovieListVideo MovieListVideo,
                              @RequestParam("video") MultipartFile video,
                              @RequestParam("moviename") String moviename) {
        try {
            MovieListVideo.setMovievideo(video.getBytes());
            MovieListVideo.setMoviename(moviename);
            // 데이터베이스에 저장
            movieListVideoRepository.save(MovieListVideo);
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
