package com.care.test.admin;

import com.care.test.member.UserRepository;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserRepository userRepository, AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/adJoin")
    public String join(){
        System.out.println("GetMapping /join");
        return "join.html";
    }
    @PostMapping("/adJoin")
    public String registerUser(@ModelAttribute("user") Admin admin) {
        System.out.println("PostMapping /join");
        System.out.println(admin.getAdminId());
        System.out.println(admin.getAdminId());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(admin.getAdminId());
        admin.setAdminPw(encodedPassword);

        //adminRepository JPA로 데이터 저장
        adminRepository.save(admin);
        return "login";
    }

    @GetMapping("/adLogin")
    public String login(){
        System.out.println("GetMapping /login");
        return "login";
    }

    @GetMapping("/userList")
    public String getAllUsers(Model model) {
        //userRepository 가져와서 해당 데이터 출력
        model.addAttribute("members", userRepository.findAll());
        return "myData"; // user-myData.html 파일과 매핑됩니다.
    }

}