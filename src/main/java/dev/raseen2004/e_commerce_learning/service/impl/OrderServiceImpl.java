package dev.raseen2004.e_commerce_learning.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.request.OrderRequest;
import dev.raseen2004.e_commerce_learning.dto.response.OrderResponse;
import dev.raseen2004.e_commerce_learning.entity.Address;
import dev.raseen2004.e_commerce_learning.entity.Cart;
import dev.raseen2004.e_commerce_learning.entity.CartItem;
import dev.raseen2004.e_commerce_learning.entity.Customer;
import dev.raseen2004.e_commerce_learning.entity.Order;
import dev.raseen2004.e_commerce_learning.entity.OrderItem;
import dev.raseen2004.e_commerce_learning.entity.Payment;
import dev.raseen2004.e_commerce_learning.entity.Product;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.OrderMapper;
import dev.raseen2004.e_commerce_learning.repository.AddressRepository;
import dev.raseen2004.e_commerce_learning.repository.CartRepository;
import dev.raseen2004.e_commerce_learning.repository.CustomerRepository;
import dev.raseen2004.e_commerce_learning.repository.OrderRepository;
import dev.raseen2004.e_commerce_learning.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;

    @Override
    public OrderResponse placeOrder(Long customerId, OrderRequest request) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> 
        new ResourceNotFoundException("Customer not found with id: "+ customerId));
        Cart cart = cartRepository
            .findByCustomerId(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cart not found for customer id: "+ customerId)
            );
        
        if(cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException(
                "Cannot place order because cart is empty"
            );
        }

        Address address = addressRepository
            .findById(request.getAddressId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Address not found with id: "+ request.getAddressId())
            );
            
        if(!address.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException(
               "Address does not belong to this customer"
            );
        }

        Order order = Order.builder()
            .orderDate(LocalDateTime.now())
            .status("PENDING")
            .totalAmount(BigDecimal.ZERO)
            .customer(customer)
            .orderItems(new ArrayList<>())
            .build();
            
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(CartItem cartItem: cart.getCartItems()) {
            Product product = cartItem.getProduct();

            if(product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException(
                    "Insufficient stock for product: "+ product.getName()
                );
            }

            BigDecimal subTotal = product.getPrice()
                                    .multiply(BigDecimal.valueOf(
                                        cartItem.getQuantity())
                                    );
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .subtotal(subTotal)
                    .order(order)
                    .product(product)
                    .build();
            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(subTotal);

            product.setStock(
                product.getStock() - cartItem.getQuantity()
            );
        }

        order.setTotalAmount(totalAmount);

        Payment payment = Payment.builder()
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("PENDING")
                .transactionId(UUID.randomUUID().toString())
                .paymentDate(LocalDateTime.now())
                .amount(totalAmount)
                .order(order)
                .build();
        
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return OrderMapper.toResponse(findOrder(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        if(!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: "+ customerId);
        }

        return orderRepository.findByCustomerId(customerId)
            .stream()
            .map(OrderMapper::toResponse)
            .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = findOrder(id);

        order.setStatus(status);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(savedOrder);
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found with id: " + id));
    }
}
