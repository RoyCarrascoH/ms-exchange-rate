package com.nttdata.bootcamp.msexchangerate.model;

import com.nttdata.bootcamp.msexchangerate.dto.DebitCardDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
