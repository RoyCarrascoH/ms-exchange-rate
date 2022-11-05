package com.nttdata.bootcamp.msexchangerate.application;

import com.nttdata.bootcamp.msexchangerate.dto.ExchangeRatetDto;
import com.nttdata.bootcamp.msexchangerate.exception.ResourceNotFoundException;
import com.nttdata.bootcamp.msexchangerate.infrastructure.*;
import com.nttdata.bootcamp.msexchangerate.model.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private CurrencyTypeRepository currencyTypeRepository;

    @Override
    public Flux<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public Mono<ExchangeRate> findById(String idExchangeRate) {
        return Mono.just(idExchangeRate)
                .flatMap(exchangeRateRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio", "idExchangeRate", idExchangeRate)));
    }

    @Override
    public Mono<ExchangeRate> save(ExchangeRatetDto exchangeRateDto) {
        log.info("----save-------ExchangeRate : " + exchangeRateDto.toString());
        return Mono.just(exchangeRateDto)
                .flatMap(mwd -> validateCurrencyType(mwd).then(Mono.just(mwd)))
                .flatMap(mwd -> mwd.MapperToExchangeRate())
                .flatMap(exchangeRateRepository::save);
    }

    @Override
    public Mono<ExchangeRate> update(ExchangeRatetDto exchangeRateDto, String idExchangeRate) {
        log.info("----update-------exchangeRateDto -- ExchangeRate: " + exchangeRateDto.toString() + " -- " + idExchangeRate);
        return Mono.just(exchangeRateDto)
                .flatMap(mwd -> validateCurrencyType(mwd).then(Mono.just(mwd)))
                .flatMap(mwd -> mwd.MapperToExchangeRate())
                .flatMap(mwd -> exchangeRateRepository.findById(idExchangeRate)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio", "idExchangeRate", idExchangeRate)))
                        .flatMap(x -> {
                            mwd.setId(x.getId());
                            return Mono.just(mwd);
                        })
                        .flatMap(exchangeRateRepository::save)
                );
    }

    @Override
    public Mono<Void> delete(String idExchangeRate) {
        return Mono.just(idExchangeRate)
                .flatMap(b -> exchangeRateRepository.findById(b))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio", "idExchangeRate", idExchangeRate)))
                .flatMap(exchangeRateRepository::delete);
    }

    public Mono<Void> validateCurrencyType(ExchangeRatetDto ExchangeRateDto) {
        log.info("--validateCurrencyType-------: ");
        return currencyTypeRepository.findCurrencyTypeByType(ExchangeRateDto.getCurrency())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo moneda", "Currency", ExchangeRateDto.getCurrency())))
                .flatMap(c -> Mono.empty());
    }
}
