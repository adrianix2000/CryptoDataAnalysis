package pl.adrianix2000.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adrianix2000.backend.Models.Entities.Quotes;

@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {
}
