package com.ohgiraffers.restapi.section02.responsentity;

import lombok.*;

import java.util.Map;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ResponseMessage {
    private int httpStatusCode;
    public String message;
    private Map<String, Object> result;
}
