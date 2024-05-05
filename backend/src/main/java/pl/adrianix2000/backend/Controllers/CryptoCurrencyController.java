package pl.adrianix2000.backend.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.adrianix2000.backend.Models.CustomHttpResponse;
import pl.adrianix2000.backend.Services.CryptoCurrencyService;

@Controller
@RestController
@RequestMapping("/currencies")
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyController {

    private final CryptoCurrencyService service;

    @PostMapping("/updateData")
    public ResponseEntity<Object> updateCryptoData(@RequestParam String cryptoCurrencyName) {
        CustomHttpResponse response = service.updateCryptoHistoricalData(cryptoCurrencyName);
        return ResponseEntity.status(response.getStatus()).body(response.getBody().get());
    }
}
