package com.testtask.demo.wire;


import com.testtask.demo.domain.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeletedWire {

    private String status;

    private LocalDate date;

    private List<Purchase> purchases;
}
