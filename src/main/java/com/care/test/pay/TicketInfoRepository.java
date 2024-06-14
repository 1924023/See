package com.care.test.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, String> {
    TicketInfo findFirstByOrderByPriceAsc();
}
