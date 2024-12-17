package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "announcement_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementImage {

    public static final int MAXIMUM_IMAGES_PER_ANNOUNCEMENT = 4 ;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    @JsonIgnore
    private Announcement announcement; // Thông báo mà hình ảnh này liên kết

    @Column(name = "image_url", length = 300)
    @JsonProperty("image_url")
    private String imageUrl; // URL của hình ảnh
}
