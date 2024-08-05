package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleLikeCreateDto;
import uz.urinov.kun.dto.ArticleLikeResponseDto;
import uz.urinov.kun.entity.ArticleLikeEntity;
import uz.urinov.kun.repository.ArticleLikeRepository;
import uz.urinov.kun.util.SecurityUtil;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    // 1. Profile Article like
    public ArticleLikeResponseDto addArticleLikeAndDislike(ArticleLikeCreateDto articleLikeCreateDto) {
      return getCheck(articleLikeCreateDto);
    }

    public ArticleLikeResponseDto getArticleLikeResponseDto(ArticleLikeEntity articleLikeEntity) {

        ArticleLikeResponseDto articleLikeResponseDto = new ArticleLikeResponseDto();
        articleLikeResponseDto.setId(articleLikeEntity.getId());
        articleLikeResponseDto.setArticleId(articleLikeEntity.getArticleId());
        articleLikeResponseDto.setProfileId(articleLikeEntity.getProfileId());
        articleLikeResponseDto.setStatus(articleLikeEntity.getStatus());
        articleLikeResponseDto.setCreatedAt(articleLikeEntity.getCreateDate());
        return articleLikeResponseDto;
    }

    public ArticleLikeResponseDto getCheck(ArticleLikeCreateDto articleLikeCreateDto) {

        Integer profileId = SecurityUtil.getProfileId();

        Optional<ArticleLikeEntity> byArticleIdAndProfileId = articleLikeRepository.findByArticleIdAndProfileId(articleLikeCreateDto.getArticleId(), profileId);

        if (byArticleIdAndProfileId.isPresent()) {

            ArticleLikeEntity articleLikeEntity = byArticleIdAndProfileId.get();

            if (articleLikeEntity.getStatus().equals(articleLikeCreateDto.getStatus())) {
                articleLikeEntity.setStatus(null);
                articleLikeRepository.save(articleLikeEntity);
                return getArticleLikeResponseDto(articleLikeEntity);
            }
            else {
                articleLikeEntity.setStatus(articleLikeCreateDto.getStatus());
                articleLikeRepository.save(articleLikeEntity);
                return getArticleLikeResponseDto(articleLikeEntity);
            }
        }
        ArticleLikeEntity articleLikeEntity = new ArticleLikeEntity();
        articleLikeEntity.setArticleId(articleLikeCreateDto.getArticleId());
        articleLikeEntity.setProfileId(profileId);
        articleLikeEntity.setStatus(articleLikeCreateDto.getStatus());
        articleLikeRepository.save(articleLikeEntity);
        return getArticleLikeResponseDto(articleLikeEntity);
    }
}
