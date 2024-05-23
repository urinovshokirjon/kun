package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.ProfileStatus;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.ProfileRepository;
import uz.urinov.kun.util.MD5Util;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public Result registration(ProfileCreateDTO dto) {
        Boolean existsedByPhoneOrEmail = profileRepository.existsByPhoneOrEmail(dto.getPhone(), dto.getEmail());
        if (existsedByPhoneOrEmail) {
            return new Result("Bunday telefon yoki email oldin ro'yxatga olingan",false);
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreateDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ROLE_INACTIVE);

        profileRepository.save(entity);

    }



}
