package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.TypesCreateDto;
import uz.urinov.kun.dto.TypesResponseDto;
import uz.urinov.kun.entity.TypesEntity;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.mapper.ArticleTypeMapper;
import uz.urinov.kun.repository.TypesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypesService {
    @Autowired
    private TypesRepository typesRepository;

    // 1. ArticleType created
    public TypesResponseDto createType(TypesCreateDto articleTypeDto) {
        TypesEntity typesEntity = new TypesEntity();
        typesEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        typesEntity.setNameUz(articleTypeDto.getNameUz());
        typesEntity.setNameRu(articleTypeDto.getNameRu());
        typesEntity.setNameEn(articleTypeDto.getNameEn());
        typesRepository.save(typesEntity);
        return toDTO(typesEntity);
    }

    // 2. Update ArticleType
    public Result updateType(int id, TypesCreateDto articleTypeDto) {
        TypesEntity typesEntity = getArticleTypeEntityById(id);
        typesEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        typesEntity.setNameUz(articleTypeDto.getNameUz());
        typesEntity.setNameRu(articleTypeDto.getNameRu());
        typesEntity.setNameEn(articleTypeDto.getNameEn());
        typesRepository.save(typesEntity);
        return new Result("ArticleTypeEntity update",true);
    }

    // 3. List ArticleType
    public PageImpl<TypesResponseDto> getArticleTypeList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<TypesEntity> articleTypeEntityList = typesRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        List<TypesResponseDto> articleTypeDtoList = articleTypeEntityList.stream().map(this::toDTO).toList();
        return new PageImpl<>(articleTypeDtoList, pageable, articleTypeEntityList.getTotalElements());
    }

    // 4. Delete ArticleType
    public Result deleteArticleType(int id) {
        TypesEntity typesEntity = getArticleTypeEntityById(id);
        typesRepository.delete(typesEntity);
        return new Result("ArticleTypeEntity delete",true);
    }

    // 5. Get By Lang ArticleType
    public PageImpl<TypesResponseDto> getArticleTypePage(int page, int size, LanguageEnum lang) {

        List<TypesResponseDto> articleTypeLangDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<TypesEntity> articleTypeEntityList = typesRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        for (TypesEntity typesEntity : articleTypeEntityList.getContent()) {

            TypesResponseDto articleTypeLangDto = new TypesResponseDto();

            articleTypeLangDto.setId(typesEntity.getId());

//            articleTypeLangDto.setOrderNumber(articleTypeEntity.getOrderNumber());

            switch (lang) {
                case UZ -> articleTypeLangDto.setNameUz(typesEntity.getNameUz());
                case RU -> articleTypeLangDto.setNameRu(typesEntity.getNameRu());
                case EN -> articleTypeLangDto.setNameEn(typesEntity.getNameEn());
            }
            articleTypeLangDtoList.add(articleTypeLangDto);
        }
        return new PageImpl<>(articleTypeLangDtoList, pageable, articleTypeEntityList.getTotalElements());

    }

    // 5. Get By Lang ArticleType
    public PageImpl<TypesResponseDto> getArticleTypePage2(int page, int size, LanguageEnum lang) {

        List<TypesResponseDto> articleTypeLangDtoList = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleTypeMapper> articleTypeEntityList = typesRepository.getArticleTypePage(lang.name(),pageable);

        for (ArticleTypeMapper articleTypeMapper : articleTypeEntityList.getContent()) {

            TypesResponseDto articleTypeLangDto = new TypesResponseDto();

            articleTypeLangDto.setId(articleTypeMapper.getId());

            articleTypeLangDto.setName(articleTypeMapper.getName());

            articleTypeLangDtoList.add(articleTypeLangDto);
        }
        return new PageImpl<>(articleTypeLangDtoList, pageable, articleTypeEntityList.getTotalElements());

    }



    public TypesResponseDto toDTO(TypesEntity entity) {
        TypesResponseDto dto = new TypesResponseDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }


    public TypesEntity getArticleTypeEntityById(int id) {
        return typesRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Category not found");
        });
    }


}
