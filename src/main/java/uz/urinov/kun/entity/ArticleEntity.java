package uz.urinov.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import uz.urinov.kun.enums.ArticleStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "title")      //   :)  davayyyyyyyyyy
    private String title;       // Yangilikning nomi

    @Column(name = "description")
    private String description; //  Yangilik haqida qisqacha malumot

    @Column(columnDefinition = "text")
    private String content;       // Malumotni to'liq qismi

    @Column(name = "shared_count")
    private Integer sharedCount;  // Yangilikni ulashilganlar soni

    @Column(name = "view_count")
    private Integer viewCount;    // Yangilikni ko'rilganlar soni

    @Column(name = "image_id")
    private Integer imageId;      // Yangilikni rasmining Id si

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();  // Yangilikni yozilgan vaqti

    @Column(name = "published_date")
    private LocalDateTime publishedDate;        // Yangilik tahrir(tekshiruv)dan o'tgan va hammaga ko'rsatilgan vaqti

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",insertable = false, updatable = false)
    private RegionEntity region;                  // BU yangilik qayer(region) da sodir bo'ldi

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",insertable = false, updatable = false)
    private CategoryEntity category;               // BU yangilik qanday category ga tegishli  bo'ladi

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id",insertable = false, updatable = false)
    private ProfileEntity moderator;                // Yangilikni yozgan odam

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id",insertable = false, updatable = false)
    private ProfileEntity publisher;                // Yangilikni tahrir(tekshirgan) odam

    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @OneToMany(mappedBy = "article")
    private List<ArticleTypesEntity> articleTypesEntity;

}
