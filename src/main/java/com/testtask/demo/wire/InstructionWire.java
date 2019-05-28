package com.testtask.demo.wire;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstructionWire {

    private List<String> instruction;

    private Map<String,Float> currency;
}
