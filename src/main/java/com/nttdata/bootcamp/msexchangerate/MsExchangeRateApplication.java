package com.nttdata.bootcamp.msexchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.nttdata.bootcamp.msexchangerate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;

/**
 * Class MsExchangeRateApplication Main.
 * ExchangeRate microservice class MsExchangeRateApplication.
 */
@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class MsExchangeRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsExchangeRateApplication.class, args);
    }

    @Bean
    public ReactiveRedisTemplate<String, ExchangeRate>
    reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, ExchangeRate>
                serializationContext = RedisSerializationContext
                .<String, ExchangeRate>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(ExchangeRate.class))
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
