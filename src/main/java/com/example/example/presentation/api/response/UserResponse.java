package com.example.example.presentation.api.response;

import com.example.example.domain.Order;
import com.example.example.domain.User;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends OkResponse {

    private final Long id;

    private final String name;

    private final String nickname;

    private final Integer phoneNumber;

    private final String email;

    private final String gender;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private OrderResponse orderResponse;

    protected UserResponse(User user) {
        super(200, "OK");

        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    protected UserResponse(User user, Order order) {
        super(200, "OK");

        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.orderResponse = OrderResponse.of(order);
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }

    public static UserResponse of(User user, Order order) {
        return new UserResponse(user, order);
    }
}
