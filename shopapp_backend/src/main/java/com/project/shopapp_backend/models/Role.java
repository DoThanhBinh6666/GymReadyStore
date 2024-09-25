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
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable=false)
    private String name;
}
