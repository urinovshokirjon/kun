package uz.urinov.kun.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String message;

    @Email
    @NotBlank
    private String email;

    @Column(name = "create_date")
    private LocalDateTime createDate;


//    @Column(name = "send_code_time")
//    private LocalDateTime sendCodeTime;
//
//    @Column(name = "verify_code_time")
//    private LocalDateTime verifyCodeTime;
}
