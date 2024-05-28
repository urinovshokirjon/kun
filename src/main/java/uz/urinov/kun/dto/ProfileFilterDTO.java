package uz.urinov.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.ProfileStatus;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDTO {

    @Min(value = 1, message = "1 dan past Id yo'q")
    private Integer profileId;

    @Size(min = 3, max = 50, message = "Berilgan name uzunligi 3 va 50 orasida bo'lishi kerak")
    private String name;

    @Size(min = 3, max = 50, message = "Berilgan surname uzunligi 3 va 50 orasida bo'lishi kerak")
    private String surname;

    @Email
    @Size(min = 3, max = 50, message = "Berilgan email uzunligi 3 va 50 orasida bo'lishi kerak")
    private String email;

    @Size(min = 12, max = 12, message = "Berilgan phone uzunligi 12 va 12 orasida bo'lishi kerak")
    private String phone;

    private String profileRole;

    private String profileStatus;

    private LocalDate createdDateFrom;

    private LocalDate createdDateTo;

}
