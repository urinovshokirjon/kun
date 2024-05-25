package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.SmsHistoryEntity;

import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity,Integer> {
    Optional<SmsHistoryEntity> findBySmsCodeAndPhone(String message, String phone);
}
