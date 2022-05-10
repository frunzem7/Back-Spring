package com.example.customer.internship.services;

import com.example.customer.internship.dto.CustomerDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO getById(Long id) throws Exception;

    HttpStatus deleteById(Long id);

    CustomerDTO update(CustomerDTO customerDTO);
}
