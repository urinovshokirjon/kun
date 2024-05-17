package uz.urinov.kun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "region")
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number",unique = true)
    private Integer orderNumber;

    @Column(name = "name_uz", length = 50, unique = true)
    private String nameUz;

    @Column(name = "name_ru", length = 50, unique = true)
    private String nameRu;

    @Column(name = "name_en", length = 50, unique = true)
    private String nameEn;

    @Column(name = "visible")
    private Boolean visible=Boolean.TRUE;

    @Column(name = "create_date")
    private LocalDate createDate=LocalDate.now();

}
