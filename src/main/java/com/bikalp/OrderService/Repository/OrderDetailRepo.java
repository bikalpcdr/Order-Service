package com.bikalp.OrderService.Repository;

import com.bikalp.OrderService.Entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetails, Long> {
}
