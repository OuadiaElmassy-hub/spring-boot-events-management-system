package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Admin;
import com.ouadia.rovista1.repositories.AdminRepository;
import com.ouadia.rovista1.services.IAdminService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private AdminRepository repository;

    public AdminServiceImpl(AdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public Admin addAdmin(Admin admin) {
        return repository.save(admin);
    }

    @Override
    public Admin editAdmin(Admin admin) {
        return repository.save(admin);
    }

    @Override
    public Admin getAdminById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Admin> getAllAdmins() {
        return repository.findAll();
    }

    @Override
    public void deleteAdminById(Long id) {
        repository.deleteById(id);
    }
}
