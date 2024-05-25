package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.SmsHistoryEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity,Integer> {
    Optional<SmsHistoryEntity> findBySmsCodeAndPhone(String message, String phone);

    // countByEmailAndCreateDateBetween
    Long countByPhoneAndCreateDateBetween(String phone, LocalDateTime from, LocalDateTime to);
}
