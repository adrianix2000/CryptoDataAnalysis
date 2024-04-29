package pl.adrianix2000.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Exceptions.ApplicationException;
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
    private final UserMapper userMapper;
    private final JWTService jwtService;

    public CustomHttpResponse addUser(UserRegistryRequest registryRequest) {
        Optional<User> foundedUser = respository.findUserByEmail(registryRequest.getEmail());

        if (foundedUser.isEmpty()) {
            respository.addUser(registryRequest);
            return CustomHttpResponse.builder()
                    .body(Optional.of("Pomyślnie dodano nowego użytkownika"))
                    .status(HttpStatus.OK)
                    .build();
        }else {
            throw new ApplicationException("Użytkownik o podanym emailu istanieje", HttpStatus.CONFLICT);
        }

    }

    public CustomHttpResponse loginUser(UserLoginRequest loginRequest) {
        Optional<User> foundedUser = respository.findUserByEmail(loginRequest.getEmail());
        if(foundedUser.isPresent()) {
            User user = foundedUser.get();

            if(user.getPassword().equals(loginRequest.getPassword())) {
                UserLoginResponse response = userMapper.UserToUserLoginResponse(user);
                response.setToken(jwtService.generateToken(user));

                return CustomHttpResponse.builder()
                        .status(HttpStatus.OK)
                        .body(Optional.of(response))
                        .build();
            } else {
                throw new ApplicationException("Niepoprawne hasło", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ApplicationException("Nie ma takiego użytkownika", HttpStatus.NOT_FOUND);
        }
    }

    public List<User> getAllUsers() {
        return respository.getAllUsers();
    }
}
