package com.springJourney.Week5Practice.DTOs;

import lombok.Data;

@Data
public class UserDTO {
    private Long userid;
    private String email;
    private String name;
    private String password;
}
