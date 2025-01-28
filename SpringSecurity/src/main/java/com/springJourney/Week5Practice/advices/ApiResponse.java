package com.springJourney.Week5Practice.advices;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;
    public ApiResponse(T data) {
        this();
        this.data = data;
    }


    public ApiResponse() {
        this.timeStamp =LocalDateTime.now();
    }


    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }

}
