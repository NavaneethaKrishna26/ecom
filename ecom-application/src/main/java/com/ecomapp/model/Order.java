package com.ecomapp.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor

public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id",nullable = false)
  private User user;
  private BigDecimal totalAmount;
  @Enumerated(EnumType.STRING)
  private OrderStatus status=OrderStatus.PENDING;
  @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
  private List<OrderItem> orderItemList=new ArrayList<>();
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;


}