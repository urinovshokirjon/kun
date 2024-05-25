package uz.urinov.kun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCreateDTO {

    @Size(min = 3, max = 50, message = "Berilgan name uzunligi 3 va 50 orasida bo'lishi kerak")
    @NotBlank(message = "name bo'sh bo'lishi mumkin emas")
    private String name;

    @Size(min = 3, max = 50, message = "Berilgan surname uzunligi 3 va 50 orasida bo'lishi kerak")
    @NotBlank(message = "Surname bo'sh bo'lishi mumkin emas")
    private String surname;

    @Email
    @Size(min = 3, max = 50, message = "Berilgan email uzunligi 3 va 50 orasida bo'lishi kerak")
    @NotBlank(message = "Email bo'sh bo'lishi mumkin emas")
    private String email;

    @Size(min = 12, max = 12, message = "Berilgan phone uzunligi 12 va 12 orasida bo'lishi kerak")
    @NotBlank(message = "Email bo'sh bo'lishi mumkin emas")
    private String phone;

    @Email
    @Size(min = 4, max = 50, message = "Berilgan password uzunligi 3 va 50 orasida bo'lishi kerak")
    @NotBlank(message = "Password bo'sh bo'lishi mumkin emas")
    private String password;

}