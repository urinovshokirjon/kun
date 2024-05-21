package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleTypeCreateDto;
import uz.urinov.kun.dto.ArticleTypeResponseDto;
import uz.urinov.kun.entity.ArticleTypeEntity;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.mapper.ArticleTypeMapper;
import uz.urinov.kun.repository.ArticleTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    // 1. ArticleType created
    public ArticleTypeResponseDto createType(ArticleTypeCreateDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        articleTypeRepository.save(articleTypeEntity);
        return toDTO(articleTypeEntity);
    }

    // 2. Update ArticleType
    public Result updateType(int id, ArticleTypeCreateDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = getArticleTypeEntityById(id);
        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        articleTypeRepository.save(articleTypeEntity);
        return new Result("ArticleTypeEntity update",true);
    }

    // 3. List ArticleType
    public PageImpl<ArticleTypeResponseDto> getArticleTypeList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleTypeEntity> articleTypeEntityList = articleTypeRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        List<ArticleTypeResponseDto> articleTypeDtoList = articleTypeEntityList.stream().map(this::toDTO).toList();
        return new PageImpl<>(articleTypeDtoList, pageable, articleTypeEntityList.getTotalElements());
    }

    // 4. Delete ArticleType
    public Result deleteArticleType(int id) {
        ArticleTypeEntity articleTypeEntity = getArticleTypeEntityById(id);
        articleTypeRepository.delete(articleTypeEntity);
        return new Result("ArticleTypeEntity delete",true);
    }

    // 5. Get By Lang ArticleType
    public PageImpl<ArticleTypeResponseDto> getArticleTypePage(int page, int size, LanguageEnum lang) {

        List<ArticleTypeResponseDto> articleTypeLangDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleTypeEntity> articleTypeEntityList = articleTypeRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        for (ArticleTypeEntity articleTypeEntity : articleTypeEntityList.getContent()) {

            ArticleTypeResponseDto articleTypeLangDto = new ArticleTypeResponseDto();

            articleTypeLangDto.setId(articleTypeEntity.getId());

//            articleTypeLangDto.setOrderNumber(articleTypeEntity.getOrderNumber());

            switch (lang) {
                case UZ -> articleTypeLangDto.setNameUz(articleTypeEntity.getNameUz());
                case RU -> articleTypeLangDto.setNameRu(articleTypeEntity.getNameRu());
                case EN -> articleTypeLangDto.setNameEn(articleTypeEntity.getNameEn());
            }
            articleTypeLangDtoList.add(articleTypeLangDto);
        }
        return new PageImpl<>(articleTypeLangDtoList, pageable, articleTypeEntityList.getTotalElements());

    }

    // 5. Get By Lang ArticleType
    public PageImpl<ArticleTypeResponseDto> getArticleTypePage2(int page, int size, LanguageEnum lang) {

        List<ArticleTypeResponseDto> articleTypeLangDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleTypeMapper> articleTypeEntityList = articleTypeRepository.getArticleTypePage(lang.name(),pageable);

        for (ArticleTypeMapper articleTypeMapper : articleTypeEntityList.getContent()) {

            ArticleTypeResponseDto articleTypeLangDto = new ArticleTypeResponseDto();

            articleTypeLangDto.setId(articleTypeMapper.getId());

            articleTypeLangDto.setName(articleTypeMapper.getName());

            articleTypeLangDtoList.add(articleTypeLangDto);
        }
        return new PageImpl<>(articleTypeLangDtoList, pageable, articleTypeEntityList.getTotalElements());

    }



    public ArticleTypeResponseDto toDTO(ArticleTypeEntity entity) {
        ArticleTypeResponseDto dto = new ArticleTypeResponseDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }


    public ArticleTypeEntity getArticleTypeEntityById(int id) {
        return articleTypeRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Category not found");
        });
    }


}
