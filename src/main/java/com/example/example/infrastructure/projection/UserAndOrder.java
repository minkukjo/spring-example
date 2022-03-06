package com.example.example.infrastructure.projection;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAndOrder {

    private Long id;

    private String name;

    private String nickname;

    private Integer phoneNumber;

    private String email;

    private String gender;

    private Long orderId;

    private String orderNumber;

    private String productName;

    private ZonedDateTime paymentDate;
}