package com.example.example.presentation.api.response;

import com.example.example.domain.Order;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponse extends OkResponse {
    private final Long id;

    private final Long userId;

    private final String orderNumber;

    private final String productName;

    private final ZonedDateTime paymentDate;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    protected OrderResponse(Order order) {
        super(200, "OK");

        this.id = order.getId();
        this.userId = order.getUserId();
        this.orderNumber = order.getOrderNumber();
        this.productName = order.getProductName();
        this.paymentDate = order.getPaymentDate();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }

    public static OrderResponse of(Order order) {
        return new OrderResponse(order);
    }
}
