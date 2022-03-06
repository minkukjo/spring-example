package com.example.example.presentation.api;

import com.example.example.application.OrderService;
import com.example.example.domain.Order;
import com.example.example.presentation.api.request.OrderRequest;
import com.example.example.presentation.api.response.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(
        value = "주문 관련 API",
        tags = "order"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(
            value = "주문 생성"
    )
    @PostMapping
    public OrderResponse order(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody OrderRequest orderRequest) {
        Long id = Long.parseLong(userDetails.getUsername());
        final Order order = orderService.create(id, orderRequest);
        return OrderResponse.of(order);
    }

    @ApiOperation(
            value = "내 주문 목록 조회"
    )
    @GetMapping
    public List<OrderResponse> find(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {
        Long id = Long.parseLong(userDetails.getUsername());
        return orderService.find(id).stream().map(OrderResponse::of)
                .collect(Collectors.toList());
    }
}
