package com.bikalp.OrderService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="order_detail")
@Data
public class OrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_detail_tb_seq_gen")
    @SequenceGenerator(name = "order_detail_tb_seq_gen", sequenceName = "order_detail_tb_seq", allocationSize = 1)
    private Long id;

    private Long productId;

    private int quantity;

    private int total;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;
}
