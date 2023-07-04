package com.example.registring;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private TestEntityManager testEntityManager;

    private TestEntityManager entityManager;

    @Test
    public void testCreateUser()
    {
        User user = new User();
        user.setEmail("gollio");
        user.setUsername("Igo");
        user.setPassword("ads4434");

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class,savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void testFindUsername()
    {
        String username = "Taras";

        User user = userRepository.findByUsername(username);

        assertThat(user).isNotNull();
    }

    @Test
    public void testRole()
    {
        User user = new User();
        user.setEmail("gollio@gmail.com");
        user.setUsername("Igor");
        user.setPassword("123");

       Role roleUser = roleRepository.findbyName("User");

       user.addRole(roleUser);

       User saveU = userRepository.save(user);

        assertThat(saveU.getRoles().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingUser() {
        User user = userRepository.findById(9L).get();
        Role roleUser = roleRepository.findbyName("User");
        Role roleAdmin = roleRepository.findbyName("Admin");

        user.addRole(roleUser);
        user.addRole(roleAdmin);


        User savedUser = userRepository.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }

}
