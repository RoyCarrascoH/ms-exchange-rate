package com.nttdata.bootcamp.msexchangerate.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class ExchangeRate.
 * ExchangeRate microservice class ExchangeRate.
 */
@Document(collection = "ExchangeRate")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    @Id
    private String id;
    private CurrencyType currencyType;
    private Double saleRate;
    private Double purchaseRate;

}
