package com.example.customer.internship.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long price;
    private Long customerId;
}
