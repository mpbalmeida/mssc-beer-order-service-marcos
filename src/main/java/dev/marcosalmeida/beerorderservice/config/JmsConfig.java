package dev.marcosalmeida.beerorderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String VALIDATE_ORDER_QUEUE = "validate-order";

    @Bean
    public MessageConverter messageConverter() {
        var converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        // Use this option to avoid full qualified classes
//        Map<String, Class<?>> typeIdMappings = new HashMap<>();
//        typeIdMappings.put("order", BeerOrder.class);
//        converter.setTypeIdMappings(typeIdMappings);

        return converter;
    }
}
