package com.example.customer.internship.controllers;

import com.example.customer.internship.dto.CustomerDTO;
import com.example.customer.internship.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/customer/")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @GetMapping("/all")
    public List<CustomerDTO> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) throws Exception {
        return customerService.getById(id);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        return customerService.deleteById(id);
    }

    @PutMapping("/")
    public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
        return customerService.update(customerDTO);
    }
}
