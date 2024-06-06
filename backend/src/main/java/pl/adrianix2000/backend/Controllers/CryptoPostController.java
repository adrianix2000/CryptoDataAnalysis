package pl.adrianix2000.backend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.adrianix2000.backend.Models.Entities.CryptoPost;
import pl.adrianix2000.backend.Services.CryptoPostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/cryptoPosts")
@RequiredArgsConstructor
public class CryptoPostController {

    final CryptoPostService service;

    @GetMapping("/allPosts")
    public ResponseEntity<List<CryptoPost>> getAllPosts() {
        return ResponseEntity.ok(service.readAllPostsFromFile());
    }

    @PostMapping("/updatePosts")
    public ResponseEntity<String> updateAllCryptoPosts() {
        service.writeAllPostFromFileToDb();
        return ResponseEntity.ok("Posts are updated");
    }
    @GetMapping("/postsForCrypto")
    public ResponseEntity<Map<String, List<CryptoPost>>> getPostForCrypto(@RequestParam String cryptoName) {
        List<CryptoPost> posts = service.getPostForCrypto(cryptoName);
        Map<String, List<CryptoPost>> result = new HashMap<>();
        result.put("posts", posts);
        return ResponseEntity.ok(result);
    }
}
