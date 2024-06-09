package pl.adrianix2000.backend.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.adrianix2000.backend.Models.DTO.AddCryptoPostsRequest;
import pl.adrianix2000.backend.Models.DTO.UserLoginResponse;
import pl.adrianix2000.backend.Models.DTO.UserRegistryRequest;
import pl.adrianix2000.backend.Models.Entities.CryptoCurrency;
import pl.adrianix2000.backend.Models.Entities.CryptoPost;
import pl.adrianix2000.backend.Models.Entities.User;

@Mapper
public interface CryptoPostMapper {

    CryptoPostMapper INSTANCE = Mappers.getMapper( CryptoPostMapper.class );

    @Mapping(source = "title", target = "title")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "positive", target = "isPositive")
    @Mapping(source = "link", target = "link")
    CryptoPost AddCryptoPostsRequestToCryptoPost(AddCryptoPostsRequest addCryptoPostsRequest);

}
