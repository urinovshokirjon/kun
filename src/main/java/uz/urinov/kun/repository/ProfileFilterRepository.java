package uz.urinov.kun.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.urinov.kun.dto.FilterResponseDTO;
import uz.urinov.kun.dto.ProfileFilterDTO;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.ProfileStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileFilterRepository {
    @Autowired
    EntityManager entityManager;

    public FilterResponseDTO<ProfileEntity> getProfileFilterPage(ProfileFilterDTO profileFilterDTO, int page, int size) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder query = new StringBuilder();

        if (profileFilterDTO.getProfileId() != null) {
            query.append(" AND p.id=:profileId");
            params.put("profileId", profileFilterDTO.getProfileId());
        }
        if (profileFilterDTO.getName() != null) {
            query.append(" AND p.name=:name");
            params.put("name", profileFilterDTO.getName());
        }
        if (profileFilterDTO.getSurname() !=null){
            query.append(" AND p.surname=:surname");
            params.put("surname", profileFilterDTO.getSurname());
        }
        if (profileFilterDTO.getEmail() != null) {
            query.append(" AND p.email=:email");
            params.put("email", profileFilterDTO.getEmail());
        }
        if (profileFilterDTO.getPhone() != null) {
            query.append(" AND p.phone=:phone");
            params.put("phone", profileFilterDTO.getPhone());
        }
        if (profileFilterDTO.getProfileRole()!=null){
            query.append(" AND p.role=:profileRole");
            params.put("profileRole", ProfileRole.valueOf(profileFilterDTO.getProfileRole()));
        }
        if (profileFilterDTO.getProfileStatus() !=null ){
            query.append(" AND p.status=:profileStatus");
            params.put("profileStatus", ProfileStatus.valueOf(profileFilterDTO.getProfileStatus()));
        }
        if (profileFilterDTO.getCreatedDateFrom() != null) {
            query.append(" AND p.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", profileFilterDTO.getCreatedDateFrom());
        }
        if (profileFilterDTO.getCreatedDateTo() != null) {
            query.append(" AND p.createdDate <= :createdDateTo ");
            params.put("createdDateTo", profileFilterDTO.getCreatedDateTo());
        }

        StringBuilder selectSQL = new StringBuilder("FROM ProfileEntity p WHERE 1=1 ");
        StringBuilder countSQL = new StringBuilder("SELECT COUNT(p) FROM ProfileEntity p WHERE 1=1 ");

        selectSQL.append(query);
        countSQL.append(query);

        Query selectQuery = entityManager.createQuery(selectSQL.toString());
        Query countQuery = entityManager.createQuery(countSQL.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page*size);
        selectQuery.setMaxResults(size);
        List<ProfileEntity> profileList = selectQuery.getResultList();

        Long count = (Long) countQuery.getSingleResult();
        return new FilterResponseDTO<>(profileList,count);


    }

}
