package com.recipee.jwt.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployDTO {
    private String id;
    private String name;
    private int age;
    private String designation;
}
