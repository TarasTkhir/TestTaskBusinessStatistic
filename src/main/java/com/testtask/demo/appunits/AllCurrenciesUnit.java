package com.testtask.demo.appunits;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllCurrenciesUnit {

    private String date;

    private String success;

    private Map<String, Float> rates;

    private String timestamp;

    private String base;


}
