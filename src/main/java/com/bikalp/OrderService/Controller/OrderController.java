package com.bikalp.OrderService.Controller;

import com.bikalp.OrderService.Entity.Order;
import com.bikalp.OrderService.Service.OrderService;
import com.bikalp.OrderService.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public ResponseEntity<OrderDto> save(@RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.save(order));
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDto> update(@RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.update(order));
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
       return ResponseEntity.ok().body(orderService.getById(id));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok().body(orderService.getAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.deleteById(id));
    }
}
