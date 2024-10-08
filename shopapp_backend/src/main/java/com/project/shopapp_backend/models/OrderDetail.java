package com.project.shopapp_backend.models;

import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="price",nullable=false)
    private Float price;

    @Column(name="number_of_products",nullable=false)
    private int numberOfProducts;

    @Column(name="total_money",nullable = false)
    private int totalMoney;

    @Column(name="color")
    private String color;
}
