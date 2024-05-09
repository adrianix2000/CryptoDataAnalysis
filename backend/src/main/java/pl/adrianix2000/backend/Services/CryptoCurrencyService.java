package pl.adrianix2000.backend.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Exceptions.ApplicationException;
import pl.adrianix2000.backend.Models.CustomHttpResponse;
import pl.adrianix2000.backend.Models.DTO.ExtremeHttpRequest;
import pl.adrianix2000.backend.Models.Entities.CryptoCurrency;
import pl.adrianix2000.backend.Models.Entities.Quotes;
import pl.adrianix2000.backend.Repositories.CryptoCurrencyRepository;
import pl.adrianix2000.backend.Repositories.QuotesRepository;
import pl.adrianix2000.backend.Configuration.*;
import pl.adrianix2000.backend.Services.Analysis.ExtremeFinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoCurrencyService {

    final private CryptoCurrencyRepository repository;
    final private QuotesService quotesService;
    final private ExtremeFinder extremeFinder;

    private CryptoCurrency getCryptoByName(String cryptoCurrencyName) {
        Optional<CryptoCurrency> optionalCryptoCurrency = repository.findByName(cryptoCurrencyName);

        if(optionalCryptoCurrency.isEmpty()) {
            throw new ApplicationException("W systemie nie ma kryptowaluty o nazwie" + cryptoCurrencyName, HttpStatus.NOT_FOUND);
        }

        return optionalCryptoCurrency.get();
    }


    public CustomHttpResponse updateCryptoHistoricalData(String cryptoCurrencyName) {

        CryptoCurrency foundedCurrency = getCryptoByName(cryptoCurrencyName);

        Optional<LocalDateTime> latestDate = foundedCurrency.getQuotes().stream()
                .map(Quotes::getDateTime).max(Comparator.naturalOrder());


        if(latestDate.isPresent()) {
            quotesService.readQuotesFromCsvToDb(latestDate.get(), foundedCurrency);
        } else {
            quotesService.readQuotesFromCsvToDb(null, foundedCurrency);
        }

        return CustomHttpResponse.builder()
                .body(Optional.of("Zaktualizowano historyczne dane rynkowe kryptowaluty " + cryptoCurrencyName))
                .status(HttpStatus.OK)
                .build();
    }


    public List<String> getExtremes(ExtremeHttpRequest request) {
        CryptoCurrency foundedCurrency = getCryptoByName(request.getCryptoCurrencyName());

        List<Quotes> currencyQuotes = foundedCurrency.getQuotes();


        return extremeFinder.findLocalMinima(currencyQuotes);
    }


}
