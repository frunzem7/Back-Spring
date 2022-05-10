package com.example.customer.internship.services;

import com.example.customer.internship.dto.OrderDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface OrderService {
    HttpStatus save(OrderDTO orderDTO) throws Exception;

    List<OrderDTO> getAll();

    OrderDTO getById(Long id);

    OrderDTO update(OrderDTO orderDTO);

    HttpStatus deleteById(Long id);
}
