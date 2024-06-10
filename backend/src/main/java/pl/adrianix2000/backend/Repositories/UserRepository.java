package pl.adrianix2000.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.adrianix2000.backend.Models.Entities.User;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);
}
