package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.LoginDto;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.entity.EmailHistoryEntity;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.ProfileStatus;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.EmailHistoryRepository;
import uz.urinov.kun.repository.ProfileRepository;
import uz.urinov.kun.util.MD5Util;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    // Profile registration
    public Result registration(ProfileCreateDTO dto) {
        Boolean existsedByPhoneOrEmail = profileRepository.existsByPhoneOrEmail(dto.getPhone(), dto.getEmail());
        if (existsedByPhoneOrEmail) {
            return new Result("Bunday telefon yoki email oldin ro'yxatga olingan", false);
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreateDate(LocalDateTime.now());
        entity.setEmailCode(UUID.randomUUID().toString());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ROLE_INACTIVE);
        profileRepository.save(entity);
        // Emailga sms yuborish methodini chaqiramiz;
        sendEmail(entity.getEmail(), entity.getEmailCode());
        // EmailHistory create
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setMessage(entity.getEmailCode());
        emailHistoryEntity.setEmail(entity.getEmail());
        emailHistoryEntity.setCreateDate(LocalDateTime.now());
        emailHistoryRepository.save(emailHistoryEntity);
        return new Result("Muvaffaqiyatli ro'yxatdan o'tdingiz. Akkounting ACTIVE qilish uchun email code tasdiqlang", true);

    }

    // Profile verifyEmail
    public Result verifyEmail(String emailCade, String email) {
        Optional<ProfileEntity> byEmailAndEmailCodeAndVisible = profileRepository.findByEmailAndEmailCode(email,emailCade);
        if (byEmailAndEmailCodeAndVisible.isPresent()) {
            ProfileEntity profileEntity = byEmailAndEmailCodeAndVisible.get();
            profileEntity.setStatus(ProfileStatus.ROLE_ACTIVE);
            profileEntity.setEmailCode(null);
            profileRepository.save(profileEntity);


            return new Result("Akkound tasdiqlandi", true);
        }
        return new Result("Akkount allaqachon tasdiqlangan ", false);
    }

    // Profile login
    public Result loginProfile(LoginDto loginDto) {
        String password = MD5Util.getMD5(loginDto.getPassword());
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailAndPasswordAndVisibleTrue(loginDto.getUsername(), password);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Bunday password yoki email yo'q", false);
        }
        return new Result("Sahifangizga hush kelibsiz "+profileEntityOptional.get().getName()+" " + profileEntityOptional.get().getSurname(), true);
    }

    public void sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Dasturlash@gmail.uz");
            message.setTo(sendingEmail);
            message.setSubject("Accountni tasdiqlash");
//            message.setText("<a href='http://localhost:8080/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang</a>");
            message.setText("http://localhost:8080/auth/verifyEmail?emailCade=" + emailCode + "&email=" + sendingEmail);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
