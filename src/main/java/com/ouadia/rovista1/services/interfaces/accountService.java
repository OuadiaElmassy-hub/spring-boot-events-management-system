package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.RoleNotFoundException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;

public interface accountService {
    Utilisateur addUser(String username , String password , String confirmPass ) throws RoleNotFoundException, UserNotFoundException;
    Utilisateur loedUserByUsername(String username);
    void setRoleToUser(String username ,String role) throws UserNotFoundException, RoleNotFoundException;
}
