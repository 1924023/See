package com.care.test.movie_list;

import jakarta.persistence.*;

@Entity
@Table(name = "allmovieinfo")
public class MovieListInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] moviethumbnail;

    private String movieactor;

    private String moviename;

    private String moviedirector;

    private String moviegenre;

    private String moviedata;

    private int viewcount; // 조회수 필드 추가


    public Long getId() {
        return id;
    }

    public byte[] getMoviethumbnail() {
        return moviethumbnail;
    }

    public String getMovieactor() {
        return movieactor;
    }

    public String getMoviedirector() {
        return moviedirector;
    }

    public String getMoviegenre() {
        return moviegenre;
    }

    public String getMoviename() {
        return moviename;
    }

    public String getMoviedata() { return moviedata; }

    public int getViewcount() {
        return viewcount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setMoviethumbnail(byte[] moviethumbnail) {
        this.moviethumbnail = moviethumbnail;
    }

    public void setMovieactor(String movieactor) {
        this.movieactor = movieactor;
    }

    public void setMoviedirector(String moviedirector) {
        this.moviedirector = moviedirector;
    }

    public void setMoviegenre(String moviegenre) {
        this.moviegenre = moviegenre;
    }

    public void setMoviedata(String moviedata) { this.moviedata = moviedata; }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}
