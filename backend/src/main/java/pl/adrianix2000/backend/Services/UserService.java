package pl.adrianix2000.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Models.CustomHttpResponse;
import pl.adrianix2000.backend.Models.DTO.UserLoginRequest;
import pl.adrianix2000.backend.Models.DTO.UserLoginResponse;
import pl.adrianix2000.backend.Models.DTO.UserRegistryRequest;
import pl.adrianix2000.backend.Models.Entities.User;
import pl.adrianix2000.backend.Models.Mappers.UserMapper;
import pl.adrianix2000.backend.Repositories.InMemoryUserRespository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryUserRespository respository;

    public CustomHttpResponse addUser(UserRegistryRequest registryRequest) {
        Optional<User> foundedUser = respository.findUserByEmail(registryRequest.getEmail());
        if(foundedUser.isEmpty()) {
            respository.addUser(registryRequest);
            return CustomHttpResponse.builder()
                    .body(Optional.of("Pomyślnie dodano nowego użytkownika"))
                    .status(HttpStatus.OK)
                    .build();
        }

        return CustomHttpResponse.builder()
                .body(Optional.of("Użytkownik o podanym emailu istanieje"))
                .status(HttpStatus.CONFLICT)
                .build();
    }

    public CustomHttpResponse loginUser(UserLoginRequest loginRequest) {
        Optional<User> foundedUser = respository.findUserByEmail(loginRequest.getEmail());
        if(foundedUser.isPresent()) {
            User user = foundedUser.get();

            if(user.getPassword().equals(loginRequest.getPassword())) {
                UserLoginResponse response = UserMapper.INSTANCE.UserToUserLoginResponse(user);

                return CustomHttpResponse.builder()
                        .status(HttpStatus.OK)
                        .body(Optional.of(response))
                        .build();
            }

            return CustomHttpResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .body(Optional.of("Niepoprawne hasło"))
                    .build();
        }

        return CustomHttpResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .body(Optional.of("Nie ma takiego użytkownika"))
                .build();
    }

    public List<User> getAllUsers() {
        return respository.getAllUsers();
    }
}
