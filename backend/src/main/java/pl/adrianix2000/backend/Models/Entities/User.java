package pl.adrianix2000.backend.Models.Entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private long id;
    private String first_name;
    private String last_name;
    private String password;
    private String email;
}
