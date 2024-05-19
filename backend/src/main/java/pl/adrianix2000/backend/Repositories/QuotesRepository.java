package pl.adrianix2000.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adrianix2000.backend.Models.Entities.Quotes;

import java.util.List;

@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {
    @Query("SELECT q FROM Quotes q WHERE q.currency.name = :cryptoName")
    List<Quotes> findByCurrencyName(String cryptoName);
}
