package pl.adrianix2000.backend.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/currencies")
@Slf4j
public class CryptoCurrencyController {

    @PostMapping("/updateData")
    public ResponseEntity<String> updateCryptoData(@RequestParam String cryptoCurrencyName) {
        log.info(cryptoCurrencyName);
        return ResponseEntity.ok("test");
    }
}
