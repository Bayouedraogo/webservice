package restaurant.Riib_noogo.demo.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import restaurant.Riib_noogo.demo.models.Order;
import restaurant.Riib_noogo.demo.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Optional<Order> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setDate(orderDetails.getDate());
            order.setDishes(orderDetails.getDishes());
            order.setStatus(orderDetails.getStatus());
            order.setCustomer(orderDetails.getCustomer());
            order.setTotalPrice(orderDetails.getTotalPrice());
            return orderRepository.save(order);
        });
    }

    @Transactional
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
