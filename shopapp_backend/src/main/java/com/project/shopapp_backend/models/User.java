package com.project.shopapp_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Builder
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="fullname",length = 100)
    private String fullName;

    @Column(name="phone_number",length = 10,nullable = false)
    private String phoneNumber;

    @Column(name="address",length = 200)
    private String address;

    @Column(name="password",length = 200,nullable=false)
    private String password;

    private boolean active;

    @Column(name="date_of_brith")
    private Date dateOfBirth;

    @Column(name="facebook_account_id")
    private String facebookAccountId;

    @Column(name="google_account_id")
    private String googleAccountId;

    @ManyToOne
    @JoinColumn(name="role_id")
    private com.project.shopapp_backend.models.Role role;
}
