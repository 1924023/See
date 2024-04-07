package com.care.test.movie;

import jakarta.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 역할을 하는 필드

    private String moviename;
    private byte[] moviethumbnail;
    private byte[] movievideo;
    private String movieactor;
    private String moviedirector;
    private String moviegenre;

    // Getter 메서드
    public Long getId() {
        return id;
    }

    public String getMoviename(){
        return moviename;
    }

    public byte[] getMoviethumbnail() {
        return moviethumbnail;
    }

    public byte[] getMovievideo() {
        return movievideo;
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

    // Setter 메서드
    public void setId(Long id) {
        this.id = id;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setMoviethumbnail(byte[] moviethumbnail) {
        this.moviethumbnail = moviethumbnail;
    }

    public void setMovievideo(byte[] movievideo) {
        this.movievideo = movievideo;
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
}
