package com.care.test.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @RequestMapping("/home")
    public String root(){

        return "home";
    }


    @GetMapping("/join")
    public String join(){
        System.out.println("GetMapping /join");
        return "join";
    }
    @GetMapping("/index")

    public String index(){
        return "index";
    }
    @PostMapping("/join")
    public String registerUser(@ModelAttribute("user") Member member) {
        System.out.println("PostMapping /join");
        System.out.println(member.getLoginid());
        System.out.println(member.getPw());
        System.out.println(member.getName());
        System.out.println(member.getBirth());
        System.out.println(member.getGender());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPw());
        member.setPw(encodedPassword);
        //회원가입 정보 db에 저장
        userRepository.save(member);
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("GetMapping /login");
        return "login";
    }

    @GetMapping("/login_success")
    public String login_success(){
        return "login_success";
    }

    @PostMapping("login")
    public String login_check(@ModelAttribute("login_data")Member member, HttpServletRequest request) {
        System.out.println("PostMapping /login");
        System.out.println(member.getLoginid());
        // 사용자가 입력한 id값과 데이터베이스에 저장된 id값을 비교
        Member foundMember = userRepository.findByLoginid(member.getLoginid());

        //사실 위에서 같은 지 아닌 지 확인하지만 조건에 따른 기능 출력을 위해 따로 분류
        if (member.getLoginid().equals(foundMember.getLoginid())) {
            String foundId = foundMember.getLoginid();
            System.out.println(foundId);

            //패스워드 일치 확인
            if (passwordEncoder.matches(member.getPw(), foundMember.getPw())) {
                HttpSession session = request.getSession(); //일치 시 session 생성
                session.setAttribute("login_success_id", foundId); //session에 일치한 id값 저장
                System.out.println("get session");
                return "redirect:/index";
            } else {
                return "redirect:login_fail";
            }
        }
        return "찾으시는 아이디가 없습니다.";
    }


    @GetMapping("/update")
    public String update(){
        System.out.println("Getmapping update");
        return "update";
    }

    @PostMapping("/update")
    public void update(@ModelAttribute("user_update")Member member){

    }

    //삭제 후 화면 출력이 좀 이상해서 점검 해야함
    @PostMapping("/delete")
    public String delete(HttpSession session){
        System.out.println("PostMapping /delete");
        String session_id = (String)session.getAttribute("login_success_id"); //세션에 있는 id값 가져와서 String으로 저장
        System.out.println("session id : " + session_id);
        Member foundId = userRepository.findByLoginid((session_id)); //해당 id값을 가진 칼럼 조회
        userRepository.delete(foundId); //그 아이디 삭제
        session.invalidate(); //해당 세션 제거
        return "home";
    }

    @PostMapping("/myData")
    public String myData(HttpSession session, Model model, Member member){
        String foundId = (String) session.getAttribute("login_success_id"); //똑같이 세션 가져옴
        member = userRepository.findByLoginid(foundId); //세션값과 id값 있는 지 확인
        List<Member> members = new ArrayList<>(); //Member형태로 List객체 생성
        members.add(member); //List에 member 인스턴스에 넣은 데이터 추가
        model.addAttribute("members", members); //모델에 해당 데이터 넣기
        return "myData";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "forward:home";
    }
}