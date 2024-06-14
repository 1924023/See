package com.care.test.pay;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService { // 사용x

    @Autowired
    private TicketInfoRepository ticketInfoRepository;

    public BigDecimal getCurrentSubscriptionPrice() {
        // DB에서 현재 구독권의 가격을 조회하는 로직
        TicketInfo ticketInfo = ticketInfoRepository.findFirstByOrderByPriceAsc();
        // 문자열 형태의 가격 정보를 BigDecimal로 변환하여 반환
        String priceString = ticketInfo.getPrice();
        // 가격 정보가 null이 아니고 비어 있지 않은 경우에만 BigDecimal로 변환
        if (priceString != null && !priceString.isEmpty()) {
            return new BigDecimal(priceString);
        } else {
            // 가격 정보가 없을 경우 기본값으로 0을 반환하거나 예외 처리
            return BigDecimal.ZERO; // 또는 다른 처리 방법을 선택
        }
    }
}