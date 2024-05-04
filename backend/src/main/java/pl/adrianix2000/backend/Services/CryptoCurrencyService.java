package pl.adrianix2000.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Exceptions.ApplicationException;
import pl.adrianix2000.backend.Models.Entities.CryptoCurrency;
import pl.adrianix2000.backend.Models.Entities.Quotes;
import pl.adrianix2000.backend.Repositories.CryptoCurrencyRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyService {

    final private CryptoCurrencyRepository repository;

    public void updateCryptoHistoricalData(String cryptoCurrencyName) {
        Optional<CryptoCurrency> optionalCryptoCurrency = repository.findByName(cryptoCurrencyName);

        if(optionalCryptoCurrency.isEmpty()) {
            throw new ApplicationException("W systemie nie ma kryptowaluty o nazwie" + cryptoCurrencyName, HttpStatus.NOT_FOUND);
        }


        CryptoCurrency foundedCurrency = optionalCryptoCurrency.get();
        Optional<Date> latestDate = foundedCurrency.getQuotes().stream()
                .map(Quotes::getDateTime).min(Comparator.naturalOrder());


        if(latestDate.isPresent()) {
            readQuotesFromCsvToDb(latestDate.get(), foundedCurrency.getName());
        } else {
            readQuotesFromCsvToDb(null, foundedCurrency.getName());
        }

    }


    public void readQuotesFromCsvToDb(Date from, String cryptoName) {
        String filePath = "coin_" + cryptoName.toLowerCase() + ".csv";
        File file = new File(filePath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> lines = bufferedReader.lines().skip(1).toList();
        }catch (FileNotFoundException ex) {

            throw new ApplicationException("Nie istnieje plik na serwerze zawierający dane historyczne dotyczące kryptowaluty o nazwie " + cryptoName,
                    HttpStatus.NOT_FOUND);

        }
    }

}
