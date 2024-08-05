package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.CommentLikeEntity;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {

    // 1. Profile Comment like and dislike
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer commentId, Integer profileId);
}
