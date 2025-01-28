package com.springJourney.Week5Practice.DTOs;

import com.springJourney.Week5Practice.Entities.Enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
    private Set<Roles> roles;
}
