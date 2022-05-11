package com.example.patientsmvc.security.service;

import com.example.patientsmvc.security.entities.AppRole;
import com.example.patientsmvc.security.entities.AppUser;
import com.example.patientsmvc.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppUserRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser savNewUser(String username, String password, String rePassword) {
       if (password.equals(rePassword)) throw new  RuntimeException("password not match");
       String hashedPWD=passwordEncoder.encode(password);
       AppUser appUser=new AppUser();
       appUser.setUserId(UUID.randomUUID().toString());
       appUser.setUsername(username);
       appUser.setPassword(hashedPWD);
       appUser.setActive(true);
       AppUser savedAppUser=appUserRepository.save(appUser);
        return savedAppUser;

    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {

        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole!=null) throw new RuntimeException("ROLE"+roleName+"already exist");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole= appRoleRepository.save(appRole);

        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
    AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser==null) throw new RuntimeException("User not found");

        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role not found");
    appUser.getAppRoles().add(appRole);

    }

    @Override
    public void removeRoleFormUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser==null) throw new RuntimeException("User not found");

        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserName(String username) {

        return appUserRepository.findByUsername(username) ;
    }
}
