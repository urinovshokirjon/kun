package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.ArticleLikeEntity;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Query("SELECT COUNT (a) FROM ArticleLikeEntity a WHERE a.articleId=?1 AND a.status = 'LIKE' ")
    Long getArticleLikeCount(String articleId);
}
