package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.kun.entity.EmailHistoryEntity;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer> {
    Optional<EmailHistoryEntity> findByEmail(String email);

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
    // select count(*) from email_history createdDate between :from and

}
