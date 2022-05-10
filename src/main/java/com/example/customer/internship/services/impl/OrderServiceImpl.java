package com.example.customer.internship.services.impl;

import com.example.customer.internship.dto.OrderDTO;
import com.example.customer.internship.entities.Customer;
import com.example.customer.internship.entities.Order;
import com.example.customer.internship.repositories.OrderRepository;
import com.example.customer.internship.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final CustomerServiceImpl customerService;
    private final OrderRepository orderRepository;

    @Override
    public HttpStatus save(OrderDTO orderDTO) throws Exception {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setCustomer(modelMapper.map(customerService.getById(orderDTO.getCustomerId()), Customer.class));
        Order savedOrder = orderRepository.save(order);
        return HttpStatus.OK;
    }

    @Override
    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getById(Long id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .orElse(null);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        Order newOrder = modelMapper.map(orderDTO, Order.class);
        Optional<Order> optionalOrder = orderRepository.findById(newOrder.getId());
        if (optionalOrder.isPresent()) {
            Order oldOrder = optionalOrder.get();
            newOrder.setId(oldOrder.getId());
            newOrder.setPrice(oldOrder.setPrice());
            orderRepository.save(newOrder);
            return modelMapper.map(newOrder, OrderDTO.class);
        }
        return null;
    }

    @Override
    public HttpStatus deleteById(Long id) {
        Optional<Order> oldOrder = orderRepository.findById(id);
        if (oldOrder.isPresent()) {
            orderRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
