package uz.urinov.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {

    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String status;

    private String role;

    private Boolean visible;

    private LocalDateTime createDate;

    private Integer photoId;
}
