package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Role;
import com.ouadia.rovista1.entities.Utilisateur;
import com.ouadia.rovista1.exceptions.RoleNotFoundException;
import com.ouadia.rovista1.exceptions.UserAlreadyExistException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.repositories.RoleRepository;
import com.ouadia.rovista1.repositories.UtilisateurRepository;
import com.ouadia.rovista1.services.interfaces.accountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class accountServiceImpl implements accountService {
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    @Override
    public Utilisateur addUser(String username, String password, String confirmPass  ) throws UserNotFoundException {
        Utilisateur U1 = utilisateurRepository.findByUsername(username);
        if(U1!=null) throw new UserAlreadyExistException("this user already exists");
        if(!password.equals(confirmPass)) throw new RuntimeException("password not match");
        Utilisateur user = Utilisateur.builder().username(username).motDePasse(passwordEncoder.encode(password)).build();
        Utilisateur savedUser = utilisateurRepository.save(user);
        return savedUser;
    }

    @Override
    public Utilisateur loedUserByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    @Override
    public void setRoleToUser(String username , String roleName) throws UserNotFoundException, RoleNotFoundException {
        Utilisateur user = utilisateurRepository.findByUsername(username);
        Role role1 = roleRepository.findByroleName(roleName);
        if(user == null) throw new UserNotFoundException("user does not exist");
        if(role1 == null) throw new RoleNotFoundException("role does not exist");
        user.getRoles().add(role1);
        utilisateurRepository.save(user);
    }


}
