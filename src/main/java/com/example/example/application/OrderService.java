package com.example.example.application;

import com.example.example.domain.Order;
import com.example.example.infrastructure.repository.OrderRepository;
import com.example.example.presentation.api.request.OrderRequest;
import com.example.example.utils.RandomUtil;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order create(Long id, OrderRequest orderRequest) {
        final Order order = Order.builder()
                .userId(id)
                .orderNumber(RandomUtil.make(12))
                .paymentDate(ZonedDateTime.now())
                .productName(orderRequest.getProductName())
                .build();
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> find(Long id) {
        return orderRepository.findByUserId(id);
    }
}
