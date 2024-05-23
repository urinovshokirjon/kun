package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.EmailHistoryEntity;

public interface EmailHistoryRepository  extends CrudRepository<EmailHistoryEntity,Integer> {
}
