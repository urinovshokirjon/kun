package uz.urinov.kun.util;

import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.exp.AppForbiddenException;

public class SecurityUtil {

    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        JwtDTO dto = JWTUtil.decode(jwt);
        return dto;
    }

    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if(!dto.getRole().equals(requiredRole)){
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }
}
