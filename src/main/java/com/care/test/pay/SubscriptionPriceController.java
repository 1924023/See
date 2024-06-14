package com.care.test.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubscriptionPriceController { // 사전검증 코드(구독권 가격 비교후 결제 진행)

    @Autowired
    private TicketInfoRepository ticketInfoRepository;

    @GetMapping("/getSubscriptionPrice")
    @ResponseBody
    public ResponseEntity<?> getSubscriptionPrice() {
        try {
            // 데이터베이스에서 현재 구독권의 가격 조회
            TicketInfo ticketInfo = ticketInfoRepository.findFirstByOrderByPriceAsc();
            String subscriptionPrice = ticketInfo.getPrice();

            // 가격을 JSON 형식으로 응답
            System.out.println("Subscription price retrieved: " + subscriptionPrice);
            return ResponseEntity.ok().body("{\"price\": \"" + subscriptionPrice + "\"}");
        } catch (Exception e) {
            // 가격 조회 실패 시 에러 응답
            System.err.println("Failed to retrieve subscription price: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"error\": \"가격 조회 실패\"}");
        }
    }
}