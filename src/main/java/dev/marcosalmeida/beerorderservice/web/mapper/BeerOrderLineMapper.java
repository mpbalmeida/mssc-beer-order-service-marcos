package dev.marcosalmeida.beerorderservice.web.mapper;

import dev.marcosalmeida.beerorderservice.domain.BeerOrderLine;
import dev.marcosalmeida.beerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
