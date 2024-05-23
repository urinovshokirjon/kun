package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.LoginDto;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    // Profile registration
    @PostMapping("/registration")
    public ResponseEntity<Result> registration( @RequestBody ProfileCreateDTO dto) {
        Result result = authService.registration(dto);
        return ResponseEntity.ok().body(result);
    }
    // Profile verifyEmail
    @GetMapping("/verifyEmail")
    public ResponseEntity<Result> verifyEmail(@RequestParam String emailCade, @RequestParam String email) {
        Result result=authService.verifyEmail(emailCade,email);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
    // Profile login
    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        Result result=authService.loginProfile(loginDto);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }


}
