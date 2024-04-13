package com.care.test.movie;

import jakarta.persistence.*;

@Entity
@Table(name = "movievideo")
public class MovieVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String moviename;
    private byte[] movievideo;

    public Long getId() {
        return id;
    }

    public byte[] getMovievideo() {
        return movievideo;
    }

    public String getMoviename(){
        return moviename;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setMovievideo(byte[] movievideo) {
        this.movievideo = movievideo;
    }

}
