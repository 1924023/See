package com.care.test.pay;

import jakarta.persistence.*;

@Entity
@Table(name = "ticketinfo") // 티켓가격 넣어놓은 테이블
public class TicketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
