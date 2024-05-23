package uz.urinov.kun.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ProfileStatus;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>{

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    int updateStatus(Integer profileId, ProfileStatus status);
}
