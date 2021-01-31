package dev.marcosalmeida.beerorderservice.services.beer;

import dev.marcosalmeida.beerorderservice.services.beer.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class BeerServiceRestTemplate implements BeerService {

    private final String BEER_PATH_V1 = "/api/v1/beer/";
    private final String BEER_UPC_PATH_V1 = "/api/v1/beer/upc/";

    private final RestTemplate restTemplate;
    private final String beerServiceHost;

    public BeerServiceRestTemplate(RestTemplateBuilder restTemplateBuilder, @Value("${sfg.brewery.beer-service-host}") String beerServiceHost) {
        this.restTemplate = restTemplateBuilder.build();
        this.beerServiceHost = beerServiceHost;
    }

    @Override
    public Optional<BeerDto> getBeerById(String uuid) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + uuid, BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + upc, BeerDto.class));
    }
}
