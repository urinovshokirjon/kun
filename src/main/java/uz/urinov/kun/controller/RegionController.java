package uz.urinov.kun.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.dto.RegionCreateDTO;
import uz.urinov.kun.dto.RegionResponseDTO;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.RegionService;
import uz.urinov.kun.util.SecurityUtil;

import java.util.List;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    // 1. Region create (ADMIN)
@PostMapping("/adm/create")
    public ResponseEntity<RegionResponseDTO> createRegion(@Valid @RequestBody RegionCreateDTO regionDto) {
        RegionResponseDTO regionResponseDTO = regionService.createRegion(regionDto);
        return ResponseEntity.ok(regionResponseDTO);
    }

    // 2. Region update (ADMIN)
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Result> updateRegion(@Valid @RequestBody RegionCreateDTO regionDto,
                                               @PathVariable("id") int id) {
        Result result = regionService.updateRegion(regionDto,id);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 3. Region list (ADMIN)
    @GetMapping("/adm/list")
    public ResponseEntity<List<RegionResponseDTO>> getRegionList() {
        List<RegionResponseDTO> regionDtoList=regionService.getRegionList();
        return ResponseEntity.status(HttpStatus.OK).body(regionDtoList);
    }

    // 4. Region delete (ADMIN)
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Result> deleteRegion( @PathVariable int id) {
        Result result = regionService.deleteRegion(id);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 5. Region By Lang
    @GetMapping("/lang")
    public ResponseEntity<List<RegionResponseDTO>> getRegionByLang2(@RequestHeader(value = "Accept-Language",defaultValue = "UZ") LanguageEnum lang) {
        List<RegionResponseDTO> regionLangDtoList=regionService.getRegionByLang(lang);
        return ResponseEntity.status(HttpStatus.OK).body(regionLangDtoList);
    }



}
