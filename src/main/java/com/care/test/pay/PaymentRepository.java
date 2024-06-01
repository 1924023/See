package com.care.test.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByTicketusername(String ticketusername);
    List<Payment> findByAmount(String amount);

    // 특정 월에 해당하는 데이터를 추출하는 JPQL 쿼리
    @Query("SELECT p FROM Payment p WHERE EXTRACT(MONTH FROM p.ticket_date) = :month")
    List<Payment> findByTicket_date_month(@Param("month") int month);

    @Query("SELECT p FROM Payment p WHERE EXTRACT(YEAR FROM p.ticket_date) = :year")
    List<Payment> findByTicket_date_year(@Param("year") int year);

}