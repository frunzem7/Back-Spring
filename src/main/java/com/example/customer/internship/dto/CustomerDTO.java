package com.example.customer.internship.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private Long numberPhone;
}
