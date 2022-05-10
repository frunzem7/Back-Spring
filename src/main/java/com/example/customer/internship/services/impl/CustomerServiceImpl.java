package com.example.customer.internship.services.impl;

import com.example.customer.internship.dto.CustomerDTO;
import com.example.customer.internship.entities.Customer;
import com.example.customer.internship.repositories.CustomerRepository;
import com.example.customer.internship.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getById(Long id) throws Exception {
        return customerRepository.findById(id)
                .map(value -> modelMapper.map(value, CustomerDTO.class))
                .orElseThrow(() -> new Exception(String.format("Customer with id %s was not found", id)));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer newCustomer = modelMapper.map(customerDTO, Customer.class);
        Optional<Customer> optionalCustomer = customerRepository.findById(newCustomer.getId());
        if (optionalCustomer.isPresent()) {
            customerRepository.save(newCustomer);

            return modelMapper.map(newCustomer, CustomerDTO.class);
        }
        return null;
    }
}
