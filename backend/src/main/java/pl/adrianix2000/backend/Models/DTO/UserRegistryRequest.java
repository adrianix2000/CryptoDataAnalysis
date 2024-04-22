package pl.adrianix2000.backend.Models.DTO;


import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class UserRegistryRequest {
    private String first_name;
    private String last_name;
    private String password;
    private String email;
}
