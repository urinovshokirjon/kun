package uz.urinov.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.ProfileEntity;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Boolean existsByPhoneOrEmail(String phone, String email);


    // 4. Profile List (ADMIN) (Pagination)
    Page<ProfileEntity> findAllByVisibleTrueOrderById( Pageable pageable);
}
