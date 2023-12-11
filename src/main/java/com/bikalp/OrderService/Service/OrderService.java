package com.bikalp.OrderService.Service;

import com.bikalp.OrderService.Entity.Order;
import com.bikalp.OrderService.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDto save(Order order);


    OrderDto update(Order order);

    OrderDto getById(Long id);



    Boolean deleteById(Long id);

    List<OrderDto> getAll();
}
