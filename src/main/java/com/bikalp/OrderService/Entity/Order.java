package com.bikalp.OrderService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_order")
@Data
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_tb_seq_gen")
    @SequenceGenerator(name = "order_tb_seq_gen", sequenceName = "order_tb_seq", allocationSize = 1)
    private Long id;

    @CreationTimestamp
    private LocalDateTime orderDate;

    private Date deliveryDate;

    private String address;

    private int grandTotal;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;

    private Long orderBy;
}
