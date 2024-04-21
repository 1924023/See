package com.care.test.movie_ani;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class MovieReviewController {

    @GetMapping("/moviesessionid")
    @ResponseBody
    public String getSessionId(HttpServletRequest request) {
        // 세션에서 로그인 ID 가져오기
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("login_success_id");

        // 세션 ID를 JSON 형식으로 응답
        return "{\"session_id\": \"" + loginId + "\"}";
    }
}
