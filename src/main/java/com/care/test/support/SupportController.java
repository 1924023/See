package com.care.test.support;

import com.care.test.member.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SupportController {

    private final SupportRepository supportRepository;

    @Autowired
    public SupportController(SupportRepository supportRepository){
        this.supportRepository = supportRepository;
    }

    @GetMapping("/support_list") // 사용자 전체의 문의 글 목록
    public String support_list(Model model){
        List<Support> supportList = supportRepository.findAll();
        model.addAttribute("admin_supportList", supportList);
        return "support/support_list";
    }

    @GetMapping("/post") // 사용자의 문의 글 페이지
    public String post(){
        return "support/post";
    }

    @PostMapping("/post") // 사용자의 문의 글 저장
    public String post(@ModelAttribute Support support){
        support.setVerify("대기중");
        support.setAdmincontent(null);
        supportRepository.save(support);
        return "redirect:/user_support_list";
    }

    @GetMapping("/user_support_list")// 사용자 개인의 문의 글 목록
    public String user_support_list(HttpSession session, Model model){
        List<Support> supportList = supportRepository.findAllByUserid((String)session.getAttribute("login_success_id"));
        model.addAttribute("supportList", supportList); //모델에 해당 데이터 넣기
        return "support/user_support_list";
    }

    @GetMapping("/user_post") // 사용자 개인의 문의글 목록 중 하나 보여주는 페이지
    public String user_post(@RequestParam String title, Model model){
        Support support = supportRepository.findByTitle(title);
        model.addAttribute("user_post", support);
        return "support/user_post";
    }

    @GetMapping("/admin_user_post") // 관리자가 답변하는 페이지
    public String admin_user_post(@RequestParam String userid,
                                  @RequestParam String title,
                                  @RequestParam String content, Model model){
        Support support = supportRepository.findAByUseridAndTitleAndContent(userid, title, content);
        model.addAttribute("admin_user_post", support);
        return "support/admin_user_post";
    }

    @PostMapping("/admin_user_post") // 관리자가 답변한 내용을 답변완료와 함께 저장
    public String admin_user_post(@ModelAttribute Support support){
        support.setVerify("답변완료");
        supportRepository.updateAdmincontent(support.getUserid(), support.getTitle(), support.getContent(), support.getAdmincontent(), support.getVerify());
        System.out.println(support);
        return "redirect:/support_list";
    }

}
