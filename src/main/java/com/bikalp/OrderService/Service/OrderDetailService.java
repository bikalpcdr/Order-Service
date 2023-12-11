package com.bikalp.OrderService.Service;

import com.bikalp.OrderService.Entity.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {

    OrderDetails save(OrderDetails orderDetails);

    OrderDetails update(OrderDetails orderDetails);

    OrderDetails getById(Long id);

    boolean deleteById(Long id);

    List<OrderDetails> getAll();
}
