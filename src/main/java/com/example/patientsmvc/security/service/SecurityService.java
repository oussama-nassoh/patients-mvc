package com.example.patientsmvc.security.service;

import com.example.patientsmvc.security.entities.AppRole;
import com.example.patientsmvc.security.entities.AppUser;

public interface SecurityService  {
    AppUser savNewUser(String username, String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFormUser(String username,String roleName);
    AppUser loadUserName(String username );
}
