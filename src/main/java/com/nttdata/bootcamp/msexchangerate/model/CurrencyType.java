package com.nttdata.bootcamp.msexchangerate.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyType {
    private String currencyType;
}
