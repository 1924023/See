package com.care.test.member;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

@Transactional
public interface UserRepository extends JpaRepository<Member, String> {
    Member findByLoginid(String loginid);
    Member deleteByLoginid(String loginid);

    @Modifying // UPDATE 쿼리 작성할 때 해당 쿼리가 update가 목적이라는 어노테이션
    @Transactional //문제 생길 때를 대비한 대비책?
    @Query("UPDATE Member s SET s.name = :name, s.gender = :gender, s.birth = :birth WHERE s.loginid = :loginid")
    void updateMember(@Param("loginid") String loginid,
                      @Param("name") String name,
                      @Param("gender") Boolean gender,
                      @Param("birth") LocalDate birth);
}