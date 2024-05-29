package uz.urinov.kun.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

}
