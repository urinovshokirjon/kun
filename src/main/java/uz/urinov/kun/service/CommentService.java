package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.CommentCreateDto;
import uz.urinov.kun.entity.CommentEntity;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.repository.CommentRepository;
import uz.urinov.kun.util.SecurityUtil;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // 1. Comment add
    public Result addComment( CommentCreateDto commentCreateDto) {

        Integer profileId = SecurityUtil.getProfileId();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentCreateDto.getContent());
        commentEntity.setArticleId(commentEntity.getArticleId());
        commentEntity.setProfileId(profileId);

        if (commentCreateDto.getReplyId() != null) {
            get(commentCreateDto.getReplyId());
            commentEntity.setReplyId(commentCreateDto.getReplyId());
        }
        commentRepository.save(commentEntity);
        return new Result("Comment saqlandi",true);
    }



    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Comment not found");
        });
    }
}
