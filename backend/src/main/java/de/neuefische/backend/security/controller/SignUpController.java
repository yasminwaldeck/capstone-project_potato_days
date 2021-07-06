package de.neuefische.backend.security.controller;

import de.neuefische.backend.security.model.CredentialsDto;
import de.neuefische.backend.security.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/signup")
public class SignUpController {

    private SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/checkusername")
    public boolean checkUsername(@RequestParam String username){
        return signUpService.checkUsername(username);
    }

    @PostMapping
    public boolean signUp(@RequestBody CredentialsDto credentials){
         return signUpService.signUp(credentials);
    }
}
