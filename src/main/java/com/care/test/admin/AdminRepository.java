package com.care.test.admin;

import com.care.test.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, String> {
    //추가하고자 하는 쿼리 여기에 작성
}