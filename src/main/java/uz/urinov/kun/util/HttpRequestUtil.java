package uz.urinov.kun.util;

import jakarta.servlet.http.HttpServletRequest;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.exp.AppForbiddenException;

public class HttpRequestUtil {

    public static JwtDTO getJwtDTO(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        JwtDTO dto =new JwtDTO(id,role);
        return dto;
    }

    public static JwtDTO getJwtDTO(HttpServletRequest request, ProfileRole requiredRole) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id, role);

        if (!dto.getRole().equals(requiredRole)) {
            throw new AppForbiddenException("Method not allowed");
        }
        return dto;
    }
}
