package restaurant.Riib_noogo.demo.dto;

import jakarta.validation.constraints.NotNull;
import restaurant.Riib_noogo.demo.models.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    private List<Long> dishIds; // IDs of dishes in the order

    @NotNull(message = "Status is mandatory")
    private OrderStatus status;

    @NotNull(message = "Customer is mandatory")
    private Long customerId;

    @NotNull(message = "Total price is mandatory")
    private Double totalPrice;

    // Getters and Setters
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Long> getDishIds() {
        return dishIds;
    }

    public void setDishIds(List<Long> dishIds) {
        this.dishIds = dishIds;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
