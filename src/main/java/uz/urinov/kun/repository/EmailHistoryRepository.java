package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.EmailHistoryEntity;

import java.util.Optional;

public interface EmailHistoryRepository  extends CrudRepository<EmailHistoryEntity,Integer> {
    Optional<EmailHistoryEntity> findByMessageAndEmail(String message, String email);
}
