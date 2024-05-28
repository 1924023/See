package com.care.test.support;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupportRepository extends JpaRepository<Support, Long>{
    List<Support> findAllByUserid(String userid);
    Support findByTitle(String title);
    List<Support> findAllByVerify(String verify);
    Support findAByUseridAndTitleAndContent(String userid, String title, String content);
    @Modifying // UPDATE 쿼리 작성할 때 해당 쿼리가 update가 목적이라는 어노테이션
    @Transactional //문제 생길 때를 대비한 대비책?
    @Query("UPDATE Support s SET s.admincontent = :admincontent, s.verify = :verify WHERE s.userid = :userid AND s.title = :title AND s.content = :content")
    void updateAdmincontent(@Param("userid") String userid,
                            @Param("title") String title,
                            @Param("content") String content,
                            @Param("admincontent") String admincontent,
                            @Param("verify") String verify);
}