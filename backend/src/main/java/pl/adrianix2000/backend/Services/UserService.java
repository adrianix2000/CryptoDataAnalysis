package pl.adrianix2000.backend.Services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Exceptions.ApplicationException;
import pl.adrianix2000.backend.Models.CustomHttpResponse;
import pl.adrianix2000.backend.Models.DTO.UserLoginRequest;
import pl.adrianix2000.backend.Models.DTO.UserLoginResponse;
import pl.adrianix2000.backend.Models.DTO.UserRegistryRequest;
import pl.adrianix2000.backend.Models.Entities.User;
import pl.adrianix2000.backend.Models.Mappers.UserMapper;
import pl.adrianix2000.backend.Repositories.InMemoryUserRespository;
import pl.adrianix2000.backend.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

//    private final InMemoryUserRespository respository;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomHttpResponse addUser(UserRegistryRequest registryRequest) {
        Optional<User> foundedUser = repository.findByEmail(registryRequest.getEmail());

        if (foundedUser.isEmpty()) {

            User newUser = userMapper.UserRegistryRequestToUser(registryRequest);
            newUser.setPassword(passwordEncoder.encode(registryRequest.getPassword()));

//            User newUser = User.builder()
//
//                    .email(registryRequest.getEmail())
//                    .first_name(registryRequest.getFirst_name())
//                    .last_name(registryRequest.getLast_name())
//                    .password(registryRequest.getPassword())
//                    .build();

            log.info(newUser.toString());

            repository.save(newUser);
            return CustomHttpResponse.builder()
                    .body(Optional.of("Pomyślnie dodano nowego użytkownika"))
                    .status(HttpStatus.OK)
                    .build();
        }else {
            throw new ApplicationException("Użytkownik o podanym emailu istanieje", HttpStatus.CONFLICT);
        }

    }

    public CustomHttpResponse loginUser(@Valid UserLoginRequest loginRequest) {
        Optional<User> foundedUser = repository.findByEmail(loginRequest.getEmail());
        if(foundedUser.isPresent()) {
            User user = foundedUser.get();

            if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
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
        return repository.findAll();
    }
}
