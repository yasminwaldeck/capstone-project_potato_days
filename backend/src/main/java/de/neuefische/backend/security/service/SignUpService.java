package de.neuefische.backend.security.service;

import de.neuefische.backend.security.model.CredentialsDto;
import de.neuefische.backend.security.model.UserCredentials;
import de.neuefische.backend.security.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    AppUserRepository appUserRepo;

    @Autowired
    public SignUpService(AppUserRepository appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    public boolean checkUsername(String username) {
        return appUserRepo.existsById(username);
    }

    public boolean signUp(CredentialsDto credentials){
        if(checkUsername((credentials.getUsername()))) {
            return false;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(credentials.getPassword());
        appUserRepo.save(UserCredentials.builder().username(credentials.getUsername()).password(encryptedPassword).build());
        return checkUsername(credentials.getUsername());
    }
}
