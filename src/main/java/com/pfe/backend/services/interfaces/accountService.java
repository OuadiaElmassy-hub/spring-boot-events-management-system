package com.pfe.backend.services.interfaces;

import com.pfe.backend.entities.Utilisateur;
import com.pfe.backend.exceptions.RoleNotFoundException;
import com.pfe.backend.exceptions.UserNotFoundException;

public interface accountService {
    Utilisateur addUser(String username , String password , String confirmPass ) throws RoleNotFoundException, UserNotFoundException;
    Utilisateur loedUserByUsername(String username);
    void setRoleToUser(String username ,String role) throws UserNotFoundException, RoleNotFoundException;
}
