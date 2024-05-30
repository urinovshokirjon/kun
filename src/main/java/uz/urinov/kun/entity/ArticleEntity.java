package uz.urinov.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;

    private String title;   // Yangilikning nomi

    private String description; /// Yangilik haqida qisqacha malumot

    @Column(columnDefinition = "text")
    private String content;       ///Malumotni to'liq qismi

    private Integer shared_count;  // Yangilikni ulashilganlar soni

    private Integer view_count;    // Yangilikni ko'rilganlar soni

    private Integer image_id;      // Yangilikni rasmining Id si

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();  // Yangilikni yozilgan vaqti

    @Column(name = "published_date")
    private LocalDateTime published_date;             // Yangilik tahrir(tekshiruv)dan o'tgan va hammaga ko'rsatilgan vaqti

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @ManyToOne
    private RegionEntity region;                      // BU yangilik qayer(region) da sodir bo'ldi

    @ManyToOne
    private CategoryEntity category;                  // BU yangilik qanday category ga tegishli  bo'ladi

    @ManyToOne
    private ProfileEntity moderator;                  // Yangilikni yozgan odam

    @ManyToOne
    private ProfileEntity publisher;                  // Yangilikni tahrir(tekshirgan) odam

    @OneToMany
    private List<ArticleTypeEntity> articleType;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

}
