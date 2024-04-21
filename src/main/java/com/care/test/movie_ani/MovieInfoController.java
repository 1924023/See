package com.care.test.movie_ani;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieInfoController { // 애니메이션DB

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @GetMapping("/movieinfoupload")
    public String uploadMovie() { return "movieinfo_upload"; }

    @GetMapping("/movie_list")
    public String MovieList() { return "movie_list"; }

    @GetMapping("/review")
    public String Review() { return "review"; }

    @PostMapping("/movieinfoupload")
    public String uploadMovie(@ModelAttribute MovieInfo movieInfo,
                              @RequestParam("thumbnail") MultipartFile thumbnail,
                              @RequestParam("moviename") String moviename,
                              @RequestParam("actor") String actor,
                              @RequestParam("director") String director,
                              @RequestParam("genre") String genre) {
        try {
            movieInfo.setMoviethumbnail(thumbnail.getBytes());
            movieInfo.setMoviename(moviename);
            movieInfo.setMovieactor(actor);
            movieInfo.setMoviedirector(director);
            movieInfo.setMoviegenre(genre);
            // 데이터베이스에 저장
            movieInfoRepository.save(movieInfo);
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
