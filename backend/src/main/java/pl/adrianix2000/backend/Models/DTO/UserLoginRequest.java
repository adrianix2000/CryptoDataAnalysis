package pl.adrianix2000.backend.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
}
