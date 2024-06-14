package com.care.test.movie_list;

import jakarta.persistence.*;

@Entity
@Table(name = "preventmovie")
public class PreventMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;
    private String userid;
    private String moviename;
    private double lastwatchedtime;


    public Long getId(){return id;}
    public String getUserid() {
        return userid;
    }

    public String getMoviename() {
        return moviename;
    }
    public Double getLastwatchedtime(){return lastwatchedtime;}


    public void setId(Long id){this.id = id;}
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setLastwatchedtime(Double lastwatchedtime){this.lastwatchedtime = lastwatchedtime; }



}