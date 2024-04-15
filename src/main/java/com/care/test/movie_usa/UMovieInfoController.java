package com.care.test.movie_usa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UMovieInfoController { // 애니메이션DB

    @Autowired
    private UMovieInfoRepository UMovieInfoRepository;

    @GetMapping("/umovieinfoupload")
    public String uploadMovie() { return "umovieinfo_upload"; }

    @PostMapping("/umovieinfoupload")
    public String uploadMovie(@ModelAttribute UMovieInfo uMovieInfo,
                              @RequestParam("thumbnail") MultipartFile thumbnail,
                              @RequestParam("moviename") String moviename,
                              @RequestParam("actor") String actor,
                              @RequestParam("director") String director,
                              @RequestParam("genre") String genre) {
        try {
            uMovieInfo.setMoviethumbnail(thumbnail.getBytes());
            uMovieInfo.setMoviename(moviename);
            uMovieInfo.setMovieactor(actor);
            uMovieInfo.setMoviedirector(director);
            uMovieInfo.setMoviegenre(genre);
            // 데이터베이스에 저장
            UMovieInfoRepository.save(uMovieInfo);
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
