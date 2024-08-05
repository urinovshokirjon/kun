package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.CommentEntity;

public interface CommentRepository extends CrudRepository<CommentEntity,Integer> {

}
