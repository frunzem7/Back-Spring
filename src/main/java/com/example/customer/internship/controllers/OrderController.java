package com.example.customer.internship.controllers;

import com.example.customer.internship.dto.OrderDTO;
import com.example.customer.internship.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public HttpStatus save(@RequestBody OrderDTO orderDTO) throws Exception {
        return orderService.save(orderDTO);
    }

    @GetMapping("/")
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PutMapping("/")
    public OrderDTO update(@RequestBody OrderDTO orderDTO) {
        return orderService.update(orderDTO);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        return orderService.deleteById(id);
    }
}
