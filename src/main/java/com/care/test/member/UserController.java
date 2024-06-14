package com.care.test.member;

import com.care.test.pay.Payment;
import com.care.test.pay.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.paymentRepository = paymentRepository;
    }
    @RequestMapping("/home")
    public String root(){

        return "home";
    }
    @GetMapping("/join")
    public String join(){
        System.out.println("GetMapping /join");
        return "join/join";
    }
    @GetMapping("/index")
    public String index(HttpSession session){
        if(session.getAttribute("login_success_id") == null){
            //alert 후 home 페이지로
            System.out.println("로그인 후 이용 가능합니다.");
            return "home";
        }else{
            System.out.println(session.getAttribute("login_success_id"));
            if(paymentRepository.findByTicketusername((String)session.getAttribute("login_success_id")) == null){
                System.out.println("이용권이 등록되어 있지 않습니다. 이용권 등록 후 이용해주세요.");
                return "home";
            }
        }
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
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("GetMapping /login");
        return "login/login";
    }

    @GetMapping("/login_success")
    public String login_success(){
        return "login/login_success";
    }

    @PostMapping("login")
    public String login_check(@ModelAttribute("login_data")Member member, HttpServletRequest request) {
        if(member.getLoginid().equals("adminlogin")){
            return "redirect:/adlogin";
        }
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
                return "redirect:/login_fail";
            }
        }
        return "찾으시는 아이디가 없습니다.";
    }


    @GetMapping("/update")
    public String update(Model model, Member member, HttpSession session){
        String foundId = (String) session.getAttribute("login_success_id"); //똑같이 세션 가져옴
        member = userRepository.findByLoginid(foundId); //세션값과 id값 있는 지 확인
        System.out.println(member.getLoginid());
        model.addAttribute("member", member);
        System.out.println("Getmapping update");
        return "user/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user_update")Member member){
        userRepository.updateMember(member.getLoginid(), member.getName(), member.getGender(), member.getBirth());
        return "redirect:/myData";
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
        return "redirect:/home";
    }

    @RequestMapping("/myData")
    public String myData(HttpSession session, Model model, Member member){
        String foundId = (String) session.getAttribute("login_success_id"); //똑같이 세션 가져옴
        member = userRepository.findByLoginid(foundId); //세션값과 id값 있는 지 확인
        List<Member> members = new ArrayList<>(); //Member형태로 List객체 생성
        members.add(member); //List에 member 인스턴스에 넣은 데이터 추가
        model.addAttribute("members", members); //모델에 해당 데이터 넣기
        return "user/myData";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }

    @RequestMapping("/subscriptuser")
    public String subscriptuser(HttpSession session, Model model){
        Payment payment = paymentRepository.findByTicketusername((String)session.getAttribute("login_success_id"));
        model.addAttribute("subscriptuser_data", payment);
        return "user/subscriptuser";
    }

    @GetMapping("/check-login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String loginSuccessId = (String) session.getAttribute("login_success_id");
        response.put("isLoggedIn", loginSuccessId != null);
        return ResponseEntity.ok(response);
    }

}