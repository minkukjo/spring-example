package com.example.example.domain;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Order extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_number", length = 12, nullable = false)
    private String orderNumber;

    @Column(name = "productName", length = 100, nullable = false)
    private String productName;

    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime paymentDate = ZonedDateTime.now();
}
