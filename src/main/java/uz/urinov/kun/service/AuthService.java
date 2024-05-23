package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.ProfileRepository;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public Result registration(RegistrationDTO dto) {
        return null;
    }
}
