package com.nttdata.bootcamp.msexchangerate.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import javax.validation.Valid;

import com.nttdata.bootcamp.msexchangerate.dto.ExchangeRatetDto;
import com.nttdata.bootcamp.msexchangerate.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.nttdata.bootcamp.msexchangerate.application.ExchangeRateService;

@RestController
@RequestMapping("/api/exchangerate")
@Slf4j
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<ExchangeRate>>> listExchangeRates() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
    }

    @GetMapping("/{idExchangeRate}")
    public Mono<ResponseEntity<ExchangeRate>> getExchangeRateDetails(@PathVariable("idExchangeRate") String idClient) {
        return service.findById(idClient).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveExchangeRate(@Valid @RequestBody Mono<ExchangeRatetDto> ExchangeRateDto) {
        Map<String, Object> request = new HashMap<>();
        return ExchangeRateDto.flatMap(bnkAcc -> service.save(bnkAcc).map(baSv -> {
                    request.put("ExchangeRatet", baSv);
                    request.put("message", "Tipo de cambio guardado con exito");
                    request.put("timestamp", new Date());
                    return ResponseEntity.created(URI.create("/api/bankaccounts/".concat(baSv.getId())))
                            .contentType(MediaType.APPLICATION_JSON).body(request);
                })
        );
    }

    @PutMapping("/{idExchangeRate}")
    public Mono<ResponseEntity<ExchangeRate>> editExchangeRate(@Valid @RequestBody ExchangeRatetDto exchangeRateDto, @PathVariable("idExchangeRate") String idExchangeRate) {
        return service.update(exchangeRateDto, idExchangeRate)
                .map(c -> ResponseEntity.created(URI.create("/api/exchangerate/".concat(idExchangeRate)))
                        .contentType(MediaType.APPLICATION_JSON).body(c));
    }

    @DeleteMapping("/{idExchangeRate}")
    public Mono<ResponseEntity<Void>> deleteExchangeRate(@PathVariable("idExchangeRate") String idExchangeRate) {
        return service.delete(idExchangeRate).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/currencyType/{currencyType}")
    public Mono<ResponseEntity<ExchangeRate>> getExchangeRateByCurrencyType(@PathVariable("currencyType") String currencyType) {
        return service.findByCurrencyType(currencyType)
                .map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}