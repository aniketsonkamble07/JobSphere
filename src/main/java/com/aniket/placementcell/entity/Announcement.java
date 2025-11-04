package com.aniket.placementcell.entity;

import com.aniket.placementcell.enums.AnnouncementType;
import com.aniket.placementcell.enums.PriorityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Content is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AnnouncementType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PriorityLevel priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by", nullable = false)
    private PlacementOfficer postedBy;

    @Column(nullable = false)
    private LocalDateTime publishDate;

    private LocalDateTime expiryDate;

    @ElementCollection
    @CollectionTable(name = "announcement_target_audience", joinColumns = @JoinColumn(name = "announcement_id"))
    @Column(name = "audience")
    private java.util.List<String> targetAudience;

    private Boolean isActive = true;
    private Integer viewsCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (publishDate == null) {
            publishDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isVisible() {
        LocalDateTime now = LocalDateTime.now();
        return isActive &&
                publishDate != null &&
                publishDate.isBefore(now) &&
                (expiryDate == null || expiryDate.isAfter(now));
    }
}