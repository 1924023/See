package com.care.test.movie_list;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MovieListInfoController {

    @Autowired
    private MovieListInfoRepository movieListInfoRepository;

    @GetMapping("/allmovieinfoupload")
    public String uploadMovie() { return "allmovieinfo_upload"; }


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
}
