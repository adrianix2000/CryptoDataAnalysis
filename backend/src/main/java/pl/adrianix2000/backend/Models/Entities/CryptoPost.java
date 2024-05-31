package pl.adrianix2000.backend.Models.Entities;

import java.time.LocalDateTime;

enum PostCategory {
    POLITICS, CULTURE, ECONOMIC, TECHNOLOGY, SECURITY, REGULATIONS, RECOMMENDATIONS, OTHER, ECOLOGY
}
public class CryptoPost {
    private String title;
    private LocalDateTime date;
    private boolean isPositive;
    private PostCategory category;
    private String link;
    private String cryptoCurrency;
}
