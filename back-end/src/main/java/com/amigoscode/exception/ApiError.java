package com.amigoscode.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter
@Getter
@Accessors(chain = true)
public class ApiError {

    private String path;
    private String message;

    private int statusCode;

    private LocalDateTime localDateTime;
}
