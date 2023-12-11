package com.bikalp.OrderService.Service.Implementation;

import com.bikalp.OrderService.Entity.Order;
import com.bikalp.OrderService.Entity.OrderDetails;
import com.bikalp.OrderService.Repository.OrderDetailRepo;
import com.bikalp.OrderService.Repository.OrderRepo;
import com.bikalp.OrderService.Service.OrderService;
import com.bikalp.OrderService.dto.OrderDto;
import com.bikalp.OrderService.dto.ProductDto;
import com.bikalp.OrderService.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;

    public OrderServiceImpl(OrderRepo orderRepo, OrderDetailRepo orderDetailRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
    }

    @Override
    @Transactional
    public OrderDto save(Order order) {
        Order saved = orderRepo.save(order);

        if (saved == null) {
            return null;
        }

        int grandTotal = 0;
        for (OrderDetails orderDetails : saved.getOrderDetails()) {
            int price = getProductPrice(orderDetails.getProductId());
            int quantity = orderDetails.getQuantity();
            int total = price * quantity;
            grandTotal = grandTotal + total;
            orderDetails.setTotal(total);
            orderDetails.setOrder(saved);
            orderDetailRepo.save(orderDetails);
        }

        saved.setGrandTotal(grandTotal);

        String userUrl = "http://192.168.0.105:9092/user/getById/" + saved.getOrderBy();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> userResponse = restTemplate.getForEntity(userUrl, UserDto.class);

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = userResponse.getBody();

            OrderDto orderDto = OrderDto.builder()
                    .id(saved.getId())
                    .address(saved.getAddress())
                    .deliveryDate(saved.getDeliveryDate())
                    .orderBy(userDto)
                    .build();

            return orderDto;
        } else {
            return null;
        }
    }


    @Override
    @Transactional
    public OrderDto update(Order order) {
        Order updatedOrder = orderRepo.save(order);

        if (updatedOrder == null) {
            return null;
        }

        String userUrl = "http://192.168.0.105:9092/user/getById/" + updatedOrder.getOrderBy();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> userResponse = restTemplate.getForEntity(userUrl, UserDto.class);

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = userResponse.getBody();

            OrderDto orderDto = OrderDto.builder()
                    .id(updatedOrder.getId())
                    .address(updatedOrder.getAddress())
                    .deliveryDate(updatedOrder.getDeliveryDate())
                    .orderBy(userDto)
                    .build();

            return orderDto;
        } else {

            return null;
        }
    }


    @Override
    @Transactional
    public OrderDto getById(Long id) {


        Order order = orderRepo.findById(id).orElse(null);

        if (order == null) {
            return null;
        }


        String userUrl = "http://192.168.0.105:9092/user/getById/" + order.getOrderBy();


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> userResponse = restTemplate.getForEntity(userUrl, UserDto.class);

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = userResponse.getBody();

            OrderDto orderDto = OrderDto.builder()
                    .id(order.getId())
                    .address(order.getAddress())
                    .deliveryDate(order.getDeliveryDate())
                    .orderBy(userDto)
                    .build();

            return orderDto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
       orderRepo.deleteById(id);
       return true;
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepo.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            String userUrl = "http://192.168.0.105:9092/user/getById/" + order.getOrderBy();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserDto> userResponse = restTemplate.getForEntity(userUrl, UserDto.class);


                UserDto userDto = userResponse.getBody();

                OrderDto orderDto = OrderDto.builder()
                        .id(order.getId())
                        .address(order.getAddress())
                        .deliveryDate(order.getDeliveryDate())
                        .orderBy(userDto)
                        .build();

                orderDtos.add(orderDto);
        }
        return orderDtos;
    }


    public int getProductPrice(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://localhost:9091/products/getById/%s", id);
        ResponseEntity<ProductDto> response
                = restTemplate.getForEntity(url, ProductDto.class);

        if (response == null || response.getBody() == null)
            return 0;

        return response.getBody().getPrice();
    }


//    public Long getUserId(Long id) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = String.format("http://192.168.0.105:9092/user/getById/%s", id);
//        ResponseEntity<UserDto> response
//                = restTemplate.getForEntity(url, UserDto.class);
//
//        if (response == null || response.getBody() == null)
//            return null;
//
//        return response.getBody().getId();
//    }

}
