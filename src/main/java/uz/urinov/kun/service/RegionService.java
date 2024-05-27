package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.mapper.RegionMapper;
import uz.urinov.kun.dto.RegionCreateDTO;
import uz.urinov.kun.dto.RegionResponseDTO;
import uz.urinov.kun.entity.RegionEntity;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.repository.RegionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // 1. Region create
    public RegionResponseDTO createRegion(RegionCreateDTO createDTO) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(createDTO.getOrderNumber());
        entity.setNameUz(createDTO.getNameUz());
        entity.setNameRu(createDTO.getNameRu());
        entity.setNameEn(createDTO.getNameEn());

        regionRepository.save(entity);
        return toDTO(entity);
    }

    // 2. Region update (ADMIN)
    public Result updateRegion(RegionCreateDTO regionDto, int id) {
      RegionEntity regionEntity=getRegionEntityById(id);
      regionEntity.setOrderNumber(regionDto.getOrderNumber());
      regionEntity.setNameUz(regionDto.getNameUz());
      regionEntity.setNameRu(regionDto.getNameRu());
      regionEntity.setNameEn(regionDto.getNameEn());
      regionRepository.save(regionEntity);
      return new Result("Region update",true);
    }

    // 3. Region list (ADMIN)
    public List<RegionResponseDTO> getRegionList() {

        List<RegionResponseDTO> regionDtoList = new ArrayList<>();

        for (RegionEntity regionEntity : regionRepository.findAll()) {
            regionDtoList.add(toDTO(regionEntity));
        }
        return regionDtoList;
    }

    //4. Region delete (ADMIN)
    public Result deleteRegion(int id) {
        RegionEntity regionEntity = getRegionEntityById(id);
        regionRepository.delete(regionEntity);
        return new Result("RegionEntity delete",true);
    }

    // 5. Region By Lang
    public List<RegionResponseDTO> getRegionByLang(LanguageEnum lang) {

        List<RegionResponseDTO> regionLangDtoList = new ArrayList<>();

        List<RegionEntity> allByVisibleTrue = regionRepository.findAllVisible();

        for (RegionEntity regionEntity : allByVisibleTrue) {

            RegionResponseDTO regionLangDto = new RegionResponseDTO();
            regionLangDto.setId(regionEntity.getId());
            switch (lang) {
                case UZ -> regionLangDto.setName(regionEntity.getNameUz());
                case RU -> regionLangDto.setName(regionEntity.getNameRu());
                case EN -> regionLangDto.setName(regionEntity.getNameEn());
            }
            regionLangDtoList.add(regionLangDto);
        }
        return regionLangDtoList;
    }

    // 5. Region By Lang (Native query)
    public List<RegionResponseDTO> getRegionByLang2(LanguageEnum lang) {

        List<RegionResponseDTO> regionLangDtoList = new ArrayList<>();

        List<RegionMapper> allByVisibleTrue = regionRepository.findAll(lang.name());

        for (RegionMapper regionMapper : allByVisibleTrue) {
            RegionResponseDTO regionLangDto = new RegionResponseDTO();
            regionLangDto.setId(regionMapper.getId());
            regionLangDto.setName(regionMapper.getName());
            regionLangDtoList.add(regionLangDto);
        }
        return regionLangDtoList;
    }


    public RegionResponseDTO toDTO(RegionEntity entity){
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public RegionEntity getRegionEntityById(int id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Region not found");
        });
    }


}
