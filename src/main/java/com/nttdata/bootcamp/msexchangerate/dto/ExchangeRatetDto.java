package com.nttdata.bootcamp.msexchangerate.dto;

import com.nttdata.bootcamp.msexchangerate.model.CurrencyType;
import com.nttdata.bootcamp.msexchangerate.model.ExchangeRate;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Slf4j
public class ExchangeRatetDto {
    @Id
    private String id;
    private Double saleRate;
    private Double purchaseRate;
    private String currency;
    private CurrencyType currencyType;

    public Mono<ExchangeRate> MapperToExchangeRate() {
        log.info("ini MapperToExchangeRate-------: ");


        CurrencyType currencyType = CurrencyType.builder()
                .currencyType(this.getCurrency())
                .build();

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .id(this.getId())
                .currencyType(currencyType)
                .saleRate(this.getSaleRate())
                .purchaseRate(this.getPurchaseRate())
                .build();
        log.info("fn MapperToExchangeRate-------: ");
        return Mono.just(exchangeRate);
    }
}