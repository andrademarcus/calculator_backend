package com.demo.calculator.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseResult {

    private ResponseRandom random;
    private Integer requestsLeft;
}
