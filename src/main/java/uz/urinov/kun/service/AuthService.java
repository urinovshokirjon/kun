package uz.urinov.kun.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.LoginDto;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.entity.EmailHistoryEntity;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.entity.SmsHistoryEntity;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.ProfileStatus;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.EmailHistoryRepository;
import uz.urinov.kun.repository.ProfileRepository;
import uz.urinov.kun.repository.SmsHistoryRepository;
import uz.urinov.kun.util.MD5Util;
import uz.urinov.kun.util.RandomUtil;

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
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    // Profile registration Email
    public Result registrationEmail(ProfileCreateDTO dto) {
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
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ROLE_INACTIVE);
        profileRepository.save(entity);
//         Emailga sms yuborish methodini chaqiramiz;
        String emailCode = UUID.randomUUID().toString();
        sendEmail(entity.getEmail(), emailCode);
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setMessage(emailCode);
        emailHistoryEntity.setEmail(entity.getEmail());
        emailHistoryEntity.setCreateDate(LocalDateTime.now());
        emailHistoryRepository.save(emailHistoryEntity);

        return new Result("Muvaffaqiyatli ro'yxatdan o'tdingiz. Akkounting ACTIVE qilish uchun email code tasdiqlang", true);

    }


    // Profile verifyEmail
    public Result verifyEmail(String emailCode, String email) {
        Optional<EmailHistoryEntity> historyEntityOptional = emailHistoryRepository.findByMessageAndEmail(emailCode, email);
        if (historyEntityOptional.isEmpty()) {
            return new Result("Email yoki emailCode xato", false);
        }
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmail(email);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Email yoki emailCode xato", false);
        }
        ProfileEntity profileEntity = profileEntityOptional.get();
        profileEntity.setStatus(ProfileStatus.ROLE_ACTIVE);
        profileRepository.save(profileEntity);
        return new Result("Akkound tasdiqlandi", true);
    }

    // Profile registration Sms
    public Result registrationSms(ProfileCreateDTO dto) {
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
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.ROLE_INACTIVE);
        profileRepository.save(entity);
        // Sms yuborish methodini chaqiramiz;

           String message = RandomUtil.getRandomSmsCode();
        String smsCode = "Bu Eskiz dan test";
        smsService.sendSms(dto.getPhone(), smsCode);
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setPhone(dto.getPhone());
        smsHistoryEntity.setSmsCode(smsCode);
        smsHistoryEntity.setCreateDate(LocalDateTime.now());
        smsHistoryRepository.save(smsHistoryEntity);
        return new Result("Muvaffaqiyatli ro'yxatdan o'tdingiz. Akkounting ACTIVE qilish uchun telefoningizga borgan sms code tasdiqlang", true);

    }

    // Profile verifySms
    public Result verifySms(String smsCode, String phone) {
        Optional<SmsHistoryEntity> smsHistoryEntityOptional = smsHistoryRepository.findBySmsCodeAndPhone(smsCode, phone);
        if (smsHistoryEntityOptional.isEmpty()) {
            return new Result("Telefon phone yoki smsCode noto'g'ri",false);
        }
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByPhone(phone);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Telefon phone yoki smsCode noto'g'ri",false);
        }
        ProfileEntity profileEntity = profileEntityOptional.get();
        profileEntity.setStatus(ProfileStatus.ROLE_ACTIVE);
        profileRepository.save(profileEntity);
        return new Result("Profile ACTIVE holatga o'tdi",true);
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
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom("urinov@gmail.uz");
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(sendingEmail);
            helper.setSubject("Accountni tasdiqlash");

//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("Dasturlash@gmail.uz");
//            message.setTo(sendingEmail);
//            message.setSubject("Accountni tasdiqlash");
////            message.setText("<a href='http://localhost:8080/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang</a>");

            String formatText = "<style>\n" +
                    "    a:link, a:visited {\n" +
                    "        background-color: #f44336;\n" +
                    "        color: white;\n" +
                    "        padding: 14px 25px;\n" +
                    "        text-align: center;\n" +
                    "        text-decoration: none;\n" +
                    "        display: inline-block;\n" +
                    "    }\n" +
                    "\n" +
                    "    a:hover, a:active {\n" +
                    "        background-color: red;\n" +
                    "    }\n" +
                    "</style>\n" +
                    "<div style=\"text-align: center\">\n" +
                    "    <h1>Welcome to kun.uz web portal</h1>\n" +
                    "    <br>\n" +
                    "    <p>Please button lick below to complete registration</p>\n" +
                    "    <div style=\"text-align: center\">\n" +
                    "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                    "    </div>";

//            String url = "http://192.168.1.251:8080/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail;
            String url = "http://localhost:8080/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail;
            String text = String.format(formatText, url);
//            message.setText(text);
//            javaMailSender.send(message);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
