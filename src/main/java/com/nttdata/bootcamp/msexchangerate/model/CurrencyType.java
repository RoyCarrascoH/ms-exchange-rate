package com.nttdata.bootcamp.msexchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class currencyType.
 * ExchangeRate microservice class currencyType.
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyType {
    private String currencyType;
}
