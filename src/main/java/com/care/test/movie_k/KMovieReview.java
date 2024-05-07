package com.care.test.movie_k;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kmoviereviews")
public class KMovieReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moviename")
    private String movieName;

    private String loginid; // 변경된 필드 이름

    @Column(name = "reviewtext")
    private String reviewText;

    @Column(name = "reviewdate")
    private LocalDateTime reviewDate;

    // 생성자
    public KMovieReview() {
    }

    public KMovieReview(String movieName, String loginid, String reviewText, LocalDateTime reviewDate) {
        this.movieName = movieName;
        this.loginid = loginid;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLoginid() { // 변경된 Getter 메서드 이름
        return loginid;
    }

    public void setLoginid(String loginid) { // 변경된 Setter 메서드 이름
        this.loginid = loginid;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}