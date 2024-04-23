package pl.adrianix2000.backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.adrianix2000.backend.Models.Mappers.UserMapper;

@Configuration
public class MappersConfig {
    @Bean
    public UserMapper createUserMapper() {
        return UserMapper.INSTANCE;
    }

}
