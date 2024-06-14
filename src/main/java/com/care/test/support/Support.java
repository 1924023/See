package com.care.test.support;

import jakarta.persistence.*;

@Entity
@Table(name = "support")
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // 고유 식별자
    private String title;      // 제목
    private String category;   // 카테고리
    private String content;    // 내용
    private String userid;     // 작성자
    private String verify;     // 답변 여부
    private String admincontent;


    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdmincontent(){return admincontent;}
    public void setAdmincontent(String admincontent){this.admincontent = admincontent;}
}
