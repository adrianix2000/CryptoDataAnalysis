package pl.adrianix2000.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adrianix2000.backend.Models.Entities.CryptoCurrency;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    Optional<CryptoCurrency> findByName(String name);
}
