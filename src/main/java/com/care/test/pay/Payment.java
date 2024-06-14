package com.care.test.pay;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ticket")
public class Payment { // 결제정보를 넣을 Entity
    @Id
    private String ticket_id; // 결제번호
    private String ticket_uid; // 고유번호
    private String ticket_name; //티켓이름(정기구독권)
    private String ticketusername; // userid
    private String amount; // 결제가격
    private LocalDate ticket_date; // 결제날짜

    public LocalDate getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(LocalDate ticket_date) {
        this.ticket_date = ticket_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(String ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public String getTicket_name() {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public String getTicketusername() {
        return ticketusername;
    }

    public void setTicketusername(String ticketusername) {
        this.ticketusername = ticketusername;
    }
}