package com.care.test.member;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "member")
public class Member {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    //이게 기본키로 지정하는 코드인 거 같은데 기본키로 지정되는 애들은 보통 int로 순서올라가는 값이라
    //얘도 자동으로 증가시켜주는 기능이 포함된 거 같다. 그래서 String으로 하면 에러가 뜬다.
    //기존엔 gpt가 Long으로 알려준 상태였고, 난 id를 입력할 때 String 타입으로 입력했다.
    //그래서 뜬 에러도 존재할 가능성이 높다.(테스트)
    private String loginid;
    private String pw;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private Boolean gender;

    public Member() {
    }

    // Getter and Setter for id
    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    // Getter and Setter for pw
    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth){
        this.birth = birth;
    }

    // Getter and Setter for gender
    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

}



