package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.*;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.ProfileService;
import uz.urinov.kun.util.JWTUtil;
import uz.urinov.kun.util.SecurityUtil;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //  1. Create profile (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<Result> createProfile(@RequestBody ProfileCreateDTO profileCreateDTO,
                                                @RequestHeader("Authorization") String token){
        SecurityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        Result result =profileService.createProfile(profileCreateDTO);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.BAD_REQUEST).body(result);
    }
    //  2. Update Profile (ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody ProfileCreateDTO profile) {
        profileService.update(id, profile);
        return ResponseEntity.ok().body(true);
    }

    // 3. Update Profile Detail (ANY) (Profile updates own details)
    @PutMapping("/update-own")
    public ResponseEntity<Result> updateProfileOwe(@Valid  @RequestBody ProfileUpdateDTO profileUpdateDTO,
                                                   @RequestHeader("Authorization") String token){
        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        Result result =profileService.updateProfileOwe(dto.getId(),profileUpdateDTO);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

   // 4. Profile List (ADMIN) (Pagination)
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> getProfilePage(@RequestParam int page,
                                                                       @RequestParam int size,
                                                                       @RequestHeader("Authorization") String token){
        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        PageImpl<ProfileResponseDTO> profileResponseDTOPage=profileService.getProfilePage(page-1,size);
        return ResponseEntity.status(HttpStatus.OK).body(profileResponseDTOPage);
    }

    // 5. Delete Profile By Id (ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteProfile(@PathVariable int id,
                                                @RequestHeader("Authorization") String token){

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        Result result =profileService.deleteProfile(id);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    //  7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    @PostMapping("page-filter")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> getProfilePageFilter(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestBody ProfileFilterDTO profileFilterDTO,
            @RequestHeader("Authorization") String token){

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
      PageImpl<ProfileResponseDTO> profileResponseDTOPage= profileService.getProfilePageFilter(page-1,size,profileFilterDTO);
      return ResponseEntity.ok().body(profileResponseDTOPage);
    }




}
