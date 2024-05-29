package uz.urinov.kun.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.kun.enums.ProfileRole;

@Setter
@Getter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
