package restaurant.Riib_noogo.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.Riib_noogo.demo.dto.OrderDTO;
import restaurant.Riib_noogo.demo.models.Customer;

import restaurant.Riib_noogo.demo.models.Order;
import restaurant.Riib_noogo.demo.services.OrderService;
import restaurant.Riib_noogo.demo.models.Dish;
import restaurant.Riib_noogo.demo.services.CustomerService;
import restaurant.Riib_noogo.demo.services.DishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(mapToDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = mapToEntity(orderDTO);
        OrderDTO createdOrder = mapToDTO(orderService.saveOrder(order));
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, mapToEntity(orderDTO))
                .map(order -> ResponseEntity.ok(mapToDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDate(order.getDate());
        orderDTO.setDishIds(order.getDishes().stream()
                .map(Dish::getId)
                .collect(Collectors.toList()));
        orderDTO.setStatus(order.getStatus());
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setTotalPrice(order.getTotalPrice());
        return orderDTO;
    }

    private Order mapToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDate(orderDTO.getDate());

        // Récupérer les plats existants et gérer les Optional
        List<Dish> dishes = orderDTO.getDishIds().stream()
                .map(dishService::getDishById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        order.setDishes(dishes);

        // Récupérer le client et gérer l'Optional
        Optional<Customer> customerOptional = customerService.getCustomerById(orderDTO.getCustomerId());
        if (customerOptional.isPresent()) {
            order.setCustomer(customerOptional.get());
        } else {
            throw new IllegalArgumentException("Customer not found with ID: " + orderDTO.getCustomerId());
        }

        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());

        return order;
    }
}
