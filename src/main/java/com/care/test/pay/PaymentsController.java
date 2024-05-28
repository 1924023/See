package com.care.test.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentsController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketInfoRepository ticketInfoRepository;

    @GetMapping("/sessionid")
    @ResponseBody
    public String getSessionId(HttpServletRequest request) {
        // 세션에서 로그인 ID 가져오기
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("login_success_id");
        System.out.println("session id : " + loginId);

        // 세션 ID를 JSON 형식으로 응답
        return "{\"session_id\": \"" + loginId + "\"}";
    }

    @PostMapping("/payment")
    public String iamport(@ModelAttribute("payment") Payment payment, HttpServletRequest request) {
        System.out.println("PostMapping /payment");
        System.out.println("Ticket id: " + payment.getTicket_id());
        System.out.println("Ticket uid: " + payment.getTicket_uid());
        System.out.println("Ticket Name: " + payment.getTicket_name());
        System.out.println("Ticket Username: " + payment.getTicketusername());
        System.out.println("Ticket date : " + payment.getTicket_date());
        System.out.println("Ticket Price: " + payment.getAmount());

        // 세션에서 로그인 ID 가져오기
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("login_success_id");
        payment.setTicketusername(loginId);
        System.out.println("로그인 ID: " + loginId); // 로그인id 확인

        // 데이터베이스에서 구독권 가격을 조회하여 결제된 가격과 비교
        // 후검증 결제 진행후 DB와 가격 비교후 데이터 저장 불일치 저장x
        // 불일치시 결제 취소코드 추가 예정
        TicketInfo ticketInfo = ticketInfoRepository.findFirstByOrderByPriceAsc();
        String paymentAmount = ticketInfo.getPrice();

        // 결제된 가격과 구독권 가격 비교
        if (paymentAmount.equals(payment.getAmount())) {
            // 가격이 일치하면 결제 정보 저장
            paymentRepository.save(payment);
            System.out.println("결제가 완료되었습니다.");
        } else {
            // 가격이 일치하지 않으면 오류 처리
            System.out.println("결제 오류: 가격이 일치하지 않습니다.");
        }

        return "index";
    }
}