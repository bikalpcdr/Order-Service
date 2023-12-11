package com.bikalp.OrderService.Service.Implementation;

import com.bikalp.OrderService.Entity.OrderDetails;
import com.bikalp.OrderService.Repository.OrderDetailRepo;
import com.bikalp.OrderService.Service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepo orderDetailRepo;

    public OrderDetailServiceImpl(OrderDetailRepo orderDetailRepo) {
        this.orderDetailRepo = orderDetailRepo;
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailRepo.save(orderDetails);
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return orderDetailRepo.save(orderDetails);
    }

    @Override
    public OrderDetails getById(Long id) {
        return orderDetailRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        orderDetailRepo.deleteById(id);
        return true;
    }

    @Override
    public List<OrderDetails> getAll() {
        return orderDetailRepo.findAll();
    }
}
