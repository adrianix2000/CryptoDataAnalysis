package pl.adrianix2000.backend.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cryptocurrencies")
@AllArgsConstructor
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "symbol")
    private String symbol;
    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private List<Quotes> quotes;
}
