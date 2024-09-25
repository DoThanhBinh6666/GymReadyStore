package com.project.shopapp_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false,length = 350)//maximun la 350 ki tu
    private String name;

    private Float price;

    @Column(name = "description")
    private String description; // mo ta san pham

    @Column(name="thumbnail",length = 300)
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
