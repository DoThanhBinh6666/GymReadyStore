package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title; // Tiêu đề thông báo

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // Nội dung của thông báo

    @Column(name = "thumbnail", length = 300)
    private String thumbnail; // Hình ảnh thumbnail của thông báo

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AnnouncementImage> announcementImages = new ArrayList<>(); // Danh sách hình ảnh liên kết với thông báo
}
