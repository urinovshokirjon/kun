package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urinov.kun.entity.ArticleTagEntity;

import java.util.List;
import java.util.Set;

public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, Integer> {

    Set<ArticleTagEntity> findAllByTagIn(Set<String> tags);

    boolean existsByTag(String tag);

}
