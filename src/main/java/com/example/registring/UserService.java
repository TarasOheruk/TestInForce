package com.example.registring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepository repo;

    @Autowired
    RoleRepository roleRepository;

    public List<User> listAll() {
        return repo.findAll();
    }
    public User get(Long id) {
        return repo.findById(id).get();
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }
    public void save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedpassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedpassword);

        repo.save(user);
    }

    public void updateUser(User user) {

        repo.save(user);
    }

    public void saveDefaultUser(User user)
    {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedpassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedpassword);
            Role role = roleRepository.findbyName("User");
            user.addRole(role);
            repo.save(user);

    }

}
