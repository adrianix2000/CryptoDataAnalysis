package pl.adrianix2000.backend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.adrianix2000.backend.Exceptions.ApplicationException;
import pl.adrianix2000.backend.Models.Entities.CryptoCurrency;
import pl.adrianix2000.backend.Models.Entities.CryptoPost;
import pl.adrianix2000.backend.Models.Entities.PostCategory;
import pl.adrianix2000.backend.Repositories.CryptoPostRepository;

import javax.swing.text.DateFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CryptoPostService {
    final String filePath = "/home/rafal/Pulpit/6 semestr/crypto/CryptoDataAnalysis/backend/src/main/resources/Data/Posts/data.tsv";
    final CryptoCurrencyService cryptoCurrencyService;
    final CryptoPostRepository repository;

    public void writeAllPostFromFileToDb() {
        List<CryptoPost> allPosts = readAllPostsFromFile();

        allPosts.forEach(p -> repository.save(p));
    }

    public List<CryptoPost> readAllPostsFromFile() {
        File file = new File(filePath);
        List<CryptoPost> allPosts = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            allPosts = bufferedReader.lines().skip(1).map(this::convertLineToCryptoPost).toList();
        } catch(FileNotFoundException ex) {
            throw new ApplicationException("File not found", HttpStatus.NO_CONTENT);
        }
        return allPosts;
    }

    public List<CryptoPost> getPostForCrypto(String cryptoCurrencyName) {
        CryptoCurrency crypto = cryptoCurrencyService.getCryptoByName(cryptoCurrencyName);
        return repository.findCryptoPostsByCryptoName(crypto.getSymbol());
    }

    private CryptoPost convertLineToCryptoPost(String line) {
        String[] splitted = line.split("\t");
        if(splitted.length != 6) {
            throw new ApplicationException("Invalid file format", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String cryptoSymbol = splitted[1];
        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoBySymbol(cryptoSymbol.toUpperCase());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return CryptoPost.builder()
                .date(LocalDate.parse(splitted[0], formatter))
                .title(splitted[2])
                .isPositive(Boolean.parseBoolean(splitted[3]))
                .category(PostCategory.valueOf(splitted[4]))
                .cryptoCurrency(cryptoCurrency)
                .link(splitted[5])
                .build();
    }
}
