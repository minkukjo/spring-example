package com.example.example.infrastructure.repository;

import com.example.example.domain.User;
import com.example.example.infrastructure.projection.UserAndOrder;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT new com.example.example.infrastructure.projection.UserAndOrder(u.id, u.name, u.nickname, u.phoneNumber, u.email, u.gender, o.id, o.orderNumber, o.productName, o.paymentDate) " +
            "FROM User u JOIN Order o ON (u.id = o.userId) " +
            "LEFT OUTER JOIN Order o2 ON (u.id = o2.userId and o.paymentDate < o2.paymentDate) WHERE o2.id IS NULL")
    Page<UserAndOrder> findLastOrderPerUser(Pageable pageable);

    @Query("SELECT new com.example.example.infrastructure.projection.UserAndOrder(u.id, u.name, u.nickname, u.phoneNumber, u.email, u.gender, o.id, o.orderNumber, o.productName, o.paymentDate) " +
            "FROM User u JOIN Order o ON (u.id = o.userId) " +
            "LEFT OUTER JOIN Order o2 ON (u.id = o2.userId and o.paymentDate < o2.paymentDate) WHERE o2.id IS NULL and u.email = :email")
    Page<UserAndOrder> findLastOrderPerUserByEmail(String email, Pageable pageable);

    @Query("SELECT new com.example.example.infrastructure.projection.UserAndOrder(u.id, u.name, u.nickname, u.phoneNumber, u.email, u.gender, o.id, o.orderNumber, o.productName, o.paymentDate) " +
            "FROM User u JOIN Order o ON (u.id = o.userId) " +
            "LEFT OUTER JOIN Order o2 ON (u.id = o2.userId and o.paymentDate < o2.paymentDate) WHERE o2.id IS NULL and u.name = :name")
    Page<UserAndOrder> findLastOrderPerUserByName(String name, Pageable pageable);
}
