package com.testtask.demo.wire;

import com.testtask.demo.domain.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllWire {

    private String startPage;

    private String next;

    private String previous;

    private List<Purchase> results;

    @Override
    public String toString() {
        return "Find All Wire: " +
                "startPage= '" + startPage + '\'' +
                ", next= '" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}
