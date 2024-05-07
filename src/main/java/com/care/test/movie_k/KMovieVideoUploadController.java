package com.care.test.movie_k;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KMovieVideoUploadController { // 애니메이션DB

    @Autowired
    private KMovieVideoRepository KMovieVideoRepository;




    @GetMapping("/kmovievideoupload")
    public String uploadMovie(){
        return "kmovie_upload";
    }

    @PostMapping("/kmovievideoupload")
    public String uploadMovie(@ModelAttribute KMovieVideo KMovieVideo,
                              @RequestParam("video") MultipartFile video,
                              @RequestParam("moviename") String moviename) {
        try {
            KMovieVideo.setMovievideo(video.getBytes());
            KMovieVideo.setMoviename(moviename);
            // 데이터베이스에 저장
            KMovieVideoRepository.save(KMovieVideo);
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