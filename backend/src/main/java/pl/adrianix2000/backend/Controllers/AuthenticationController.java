package pl.adrianix2000.backend.Controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.adrianix2000.backend.Models.CustomHttpResponse;
import pl.adrianix2000.backend.Models.DTO.UserLoginRequest;
import pl.adrianix2000.backend.Models.DTO.UserRegistryRequest;
import pl.adrianix2000.backend.Services.UserService;

@Controller
@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;

    @PostMapping("/registry")
    public ResponseEntity<Object> registry(@RequestBody UserRegistryRequest request) {
        CustomHttpResponse response = service.addUser(request);
        return ResponseEntity.status(response.getStatus()).body(response.getBody().get());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request) {
        CustomHttpResponse response = service.loginUser(request);

        return ResponseEntity.status(response.getStatus())
                .body(response.getBody().get());

    }

}
