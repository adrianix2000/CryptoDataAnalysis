package pl.adrianix2000.backend.Models.Entities;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quotes")
public class Quotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quote_date")
    private Date dateTime;
    @Column(name = "high_price")
    private BigDecimal high_price;
    @Column(name = "low_price")
    private BigDecimal low_price;
    @Column(name = "open_price")
    private BigDecimal open_price;
    @Column(name = "close_price")
    private BigDecimal close_price;
    @Column(name = "volume")
    private BigDecimal volume;
    @Column(name = "marketcap")
    private BigDecimal marketcap;
    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    private CryptoCurrency currency;
}
