package uz.urinov.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Boolean existsByPhoneOrEmail(String phone, String email);


    // 4. Profile List (ADMIN) (Pagination)
    Page<ProfileEntity> findAllByVisibleTrueOrderById( Pageable pageable);

    // Profile registration
    Optional<ProfileEntity> findByEmail(String email);

    // Profile login
    Optional<ProfileEntity> findByEmailAndPasswordAndVisibleTrue(String email, String password);

    // Profile verifySms
    Optional<ProfileEntity> findByPhone(String phone);

    // Resent Email code
    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);


}
