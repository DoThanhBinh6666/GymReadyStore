package com.project.shopapp_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="social_account")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="provider",length = 10,nullable = false)
    private String provider;

    @Column(name="provider_id",length = 10,nullable = false)
    private String providerId;

    @Column(name="name",length = 150)
    private String name;

    @Column(name="email",length = 150)
    private String email;
}
