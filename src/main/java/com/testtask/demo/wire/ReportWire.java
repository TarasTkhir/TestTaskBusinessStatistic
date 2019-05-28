package com.testtask.demo.wire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportWire {

    private Integer year;

    private String currency;

    private double totalIncome;

}
