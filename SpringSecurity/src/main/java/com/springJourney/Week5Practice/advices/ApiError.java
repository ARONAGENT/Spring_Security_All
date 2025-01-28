package com.springJourney.Week5Practice.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime localDateTime;
    private HttpStatus status;
    private String error;

    public ApiError(){
        this.localDateTime=LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus status) {
        this();
        this.error = error;
        this.status = status;
    }
}
