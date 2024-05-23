package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.entity.EmailHistoryEntity;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.repository.EmailHistoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public void crete(String toEmail, String text) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toEmail);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public void checkEmailLimit(String email) { // 1 minute -3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >= 3) {
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }

}
