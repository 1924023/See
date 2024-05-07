package com.care.test.movie_ani;

import jakarta.persistence.*;

@Entity
@Table(name = "moviereviews")
public class MovieReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moviename")
    private String movieName;

    private String loginid; // 변경된 필드 이름

    @Column(name = "reviewtext")
    private String reviewText;


    // 생성자
    public MovieReview() {
    }

    public MovieReview(String movieName, String loginid, String reviewText) {
        this.movieName = movieName;
        this.loginid = loginid;
        this.reviewText = reviewText;
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

}
