package pl.adrianix2000.backend.Repositories;

import org.springframework.stereotype.Repository;
import pl.adrianix2000.backend.Models.DTO.UserRegistryRequest;
import pl.adrianix2000.backend.Models.Entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryUserRespository {

    private List<User> users;

    public InMemoryUserRespository() {
        users = new ArrayList<>();

        users.add(new User(1, "Adrian", "Sak", "test1", "daudshlock@gmail.com"));
        users.add(new User(2, "Rafał", "Seredowski", "test2", "rafals123@gmail.com"));
    }


    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public boolean addUser(UserRegistryRequest user) {

        User newUser = User.builder()
                .id(users.size() + 1)
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();


        users.add(newUser);

        return true;
    }
}
