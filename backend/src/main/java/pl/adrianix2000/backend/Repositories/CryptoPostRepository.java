package pl.adrianix2000.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.adrianix2000.backend.Models.Entities.CryptoPost;

import java.util.List;

public interface CryptoPostRepository extends JpaRepository<CryptoPost, Long> {

    @Query("SELECT cp FROM CryptoPost cp WHERE cp.cryptoCurrency.symbol =  :cryptoSymbol")
    List<CryptoPost> findCryptoPostsByCryptoName(@Param("cryptoSymbol") String cryptoSymbol);
}
