package uz.urinov.kun.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.*;
import uz.urinov.kun.entity.ArticleLikeEntity;
import uz.urinov.kun.entity.CommentLikeEntity;
import uz.urinov.kun.repository.CommentLikeRepository;
import uz.urinov.kun.util.SecurityUtil;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    // 1. Profile Comment like and dislike

    public CommentLikeResponseDto addCommentLikeAndDislike(CommentLikeCreateDto commentLikeCreateDto) {
        return getCheck(commentLikeCreateDto);
    }

    public CommentLikeResponseDto getCommentLikeResponseDto(CommentLikeEntity commentLikeEntity) {

        CommentLikeResponseDto commentLikeResponseDto = new CommentLikeResponseDto();
        commentLikeResponseDto.setId(commentLikeEntity.getId());
        commentLikeResponseDto.setCommentId(commentLikeEntity.getCommentId());
        commentLikeResponseDto.setProfileId(commentLikeEntity.getProfileId());
        commentLikeResponseDto.setStatus(commentLikeEntity.getStatus());
        commentLikeResponseDto.setCreatedAt(commentLikeEntity.getCreateDate());
        return commentLikeResponseDto;
    }

    public CommentLikeResponseDto getCheck(CommentLikeCreateDto commentLikeCreateDto) {

        Integer profileId = SecurityUtil.getProfileId();

        Optional<CommentLikeEntity> optionalCommentLikeEntity = commentLikeRepository.findByCommentIdAndProfileId(commentLikeCreateDto.getCommentId(), profileId);

        if (optionalCommentLikeEntity.isPresent()) {

            CommentLikeEntity commentLikeEntity = optionalCommentLikeEntity.get();

            if (commentLikeEntity.getStatus().equals(commentLikeCreateDto.getStatus())) {
                commentLikeEntity.setStatus(null);
                commentLikeRepository.save(commentLikeEntity);
                return getCommentLikeResponseDto(commentLikeEntity);
            }

            else {
                commentLikeEntity.setStatus(commentLikeCreateDto.getStatus());
                commentLikeRepository.save(commentLikeEntity);
                return getCommentLikeResponseDto(commentLikeEntity);
            }
        }

        CommentLikeEntity commentLikeEntity=new CommentLikeEntity();
        commentLikeEntity.setCommentId(commentLikeCreateDto.getCommentId());
        commentLikeEntity.setProfileId(profileId);
        commentLikeEntity.setStatus(commentLikeCreateDto.getStatus());
        commentLikeRepository.save(commentLikeEntity);
        return getCommentLikeResponseDto(commentLikeEntity);
    }
}
