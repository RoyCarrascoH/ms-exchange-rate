package com.nttdata.bootcamp.msexchangerate.infrastructure;

import com.nttdata.bootcamp.msexchangerate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ExchangeRateRepository {
    private final ReactiveRedisOperations<String, ExchangeRate> reactiveRedisOperations;

    public Flux<ExchangeRate> findAll() {
        return this.reactiveRedisOperations.<String, ExchangeRate>opsForHash().values("exchangeRates");
    }

    public Mono<ExchangeRate> findById(String id) {
        return this.reactiveRedisOperations.<String, ExchangeRate>opsForHash().get("exchangeRates", id);
    }

    public Mono<ExchangeRate> findByCurrencyType(String currencyType) {
        return Mono.just(currencyType)
                .flatMap(ct -> this.findAll()
                        .collectList()
                        .flatMap(l -> {
                            Optional<ExchangeRate> cType = l.stream()
                                    .filter(c -> c.getCurrencyType().getCurrencyType().equals(ct))
                                    .findFirst();
                            if (cType.isPresent()) {
                                return Mono.just(cType.get());
                            } else {
                                return Mono.empty();
                            }

                        })
                );
    }

    public Mono<ExchangeRate> save(ExchangeRate exchangeRate) {
        if (exchangeRate.getId() == null) {
            String id = UUID.randomUUID().toString();
            exchangeRate.setId(id);
        }
        return this.reactiveRedisOperations.<String, ExchangeRate>opsForHash()
                .put("exchangeRates", exchangeRate.getId(), exchangeRate).log().map(p -> exchangeRate);
    }

    public Mono<Void> delete(ExchangeRate currencyType) {
        return this.reactiveRedisOperations.<String, ExchangeRate>opsForHash()
                .remove("currencyTypes", currencyType.getId())
                .flatMap(p -> Mono.just(currencyType)).then();
    }

}