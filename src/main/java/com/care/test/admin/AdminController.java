package com.care.test.admin;

import com.care.test.member.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @GetMapping("/adjoin")
    public String join(){
        System.out.println("GetMapping /adjoin");
        return "join/join_admin";
    }
    @PostMapping("/adjoin")
    public String registerAdmin(@ModelAttribute("admin_id") Admin admin, Model model) {
        System.out.println("PostMapping /adjoin");
        System.out.println(admin.getAdminid());
        System.out.println(admin.getAdminpw());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(admin.getAdminpw());
        admin.setAdminpw(encodedPassword);
        //회원가입 정보 db에 저장
        adminRepository.save(admin);
        return "login/adlogin";
    }
    @GetMapping("/adlogin")
    public String login(){
        return "login/login_admin";
    }
    @PostMapping("/adlogin")
    public String login(@ModelAttribute("login_data_admin")Admin admin, HttpServletRequest request){
        Admin foundAdmin = adminRepository.findByAdminid(admin.getAdminid());

        //사실 위에서 같은 지 아닌 지 확인하지만 조건에 따른 기능 출력을 위해 따로 분류
        if (admin.getAdminid().equals(foundAdmin.getAdminid())) {
            String foundId = foundAdmin.getAdminid();
            System.out.println(foundId);

            //패스워드 일치 확인
            if (passwordEncoder.matches(admin.getAdminpw(), foundAdmin.getAdminpw())) {
                HttpSession session = request.getSession(); //일치 시 session 생성
                session.setAttribute("admin_login_id", foundId); //session에 일치한 id값 저장
                System.out.println("get session");
                return "admin_index";
            } else {
                return "redirect:login/login_fail";
            }
        }
        return "찾으시는 아이디가 없습니다.";
    }

    @GetMapping("/userlist")
    public String getAllUsers(Model model) {
        //userRepository 가져와서 해당 데이터 출력
        model.addAttribute("members", userRepository.findAll());
        return "admin/userList"; // user-myData.html 파일과 매핑됩니다.
    }

    @PostMapping("/addelete")
    public String deleteAdmin(HttpSession session){
        System.out.println("PostMapping /delete");
        String session_id = (String)session.getAttribute("admin_login_id"); //세션에 있는 id값 가져와서 String으로 저장
        System.out.println("session id : " + session_id);
        Admin foundId = adminRepository.findByAdminid((session_id)); //해당 id값을 가진 칼럼 조회
        adminRepository.delete(foundId); //그 아이디 삭제
        session.invalidate(); //해당 세션 제거
        return "home";
    }

    @GetMapping("/adupdate")
    public String update(Model model, Admin admin, HttpSession session){
        String foundId = (String) session.getAttribute("admin_login_id"); //똑같이 세션 가져옴
        admin = adminRepository.findByAdminid(foundId); //세션값과 id값 있는 지 확인
        System.out.println(admin.getAdminid());
        model.addAttribute("admin", admin);
        System.out.println("Getmapping update");
        return "admin/update";
    }

    @PostMapping("/adupdate")
    public String update(@ModelAttribute("user_update")Admin admin){
        String encodedPassword = passwordEncoder.encode(admin.getAdminpw());
        admin.setAdminpw(encodedPassword);
        adminRepository.save(admin);
        return "user/myData";
    }
}