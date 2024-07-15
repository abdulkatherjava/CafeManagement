package com.habibi.CafeManagement.service;

import com.habibi.CafeManagement.dto.OrderDto;
import com.habibi.CafeManagement.dto.OrderItemDto;
import com.habibi.CafeManagement.entity.MenuItem;
import com.habibi.CafeManagement.entity.Order;
import com.habibi.CafeManagement.entity.OrderItem;
import com.habibi.CafeManagement.repository.MenuItemRepository;
import com.habibi.CafeManagement.repository.OrderItemRepository;
import com.habibi.CafeManagement.repository.OrderRepository;
import com.habibi.CafeManagement.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    public Order createOrder(OrderDto orderDto) {
        List<OrderItem> orderItems = new ArrayList<>();
        Long totalAmount = 0L;
        Date date = Utility.localDateWithTime();

        for (OrderItemDto orderItemDto : orderDto.getOrderItemList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setCreatedDate(date);
            orderItems.add(orderItem);

            MenuItem menuItem = menuItemRepository.findById(orderItemDto.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu Item not found for ID: " + orderItemDto.getMenuItemId()));;
            orderItem.setMenuItemId(menuItem.getMenuItemId());
            orderItem.setMenuItemName(menuItem.getMenuItemName());
            totalAmount += menuItem.getMenuItemPrice() * orderItemDto.getQuantity();
        }
        Order order = new Order();
        order.setOrderDate(date);
        order.setCreatedDate(date);
        order.setTotalAmount(totalAmount);
        order.setOrderMode(orderDto.getOrderMode());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setComments(orderDto.getComments());
        Order savedOrder = orderRepository.save(order);

        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(savedOrder);
        }

        

        List<OrderItem> saveOrderItems = orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItemList(saveOrderItems);
        return savedOrder;
    }

    public Order updateOrder(Integer orderId, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found for ID: " + orderId));

        Date currentDate = Utility.localDateWithTime();

        // Update order fields
        existingOrder.setOrderMode(orderDto.getOrderMode());
        existingOrder.setOrderStatus(orderDto.getOrderStatus());
        existingOrder.setComments(orderDto.getComments());
        existingOrder.setUpdatedDate(currentDate);

        // Update order items
        List<OrderItem> existingOrderItems = existingOrder.getOrderItemList();
        List<OrderItem> updatedOrderItems = new ArrayList<>();
        Long totalAmount = 0L;

        for (OrderItemDto orderItemDto : orderDto.getOrderItemList()) {
            OrderItem orderItem = existingOrderItems.stream()
                    .filter(item -> item.getMenuItemId().equals(orderItemDto.getMenuItemId()))
                    .findFirst()
                    .orElse(new OrderItem());
            MenuItem menuItem = menuItemRepository.findById(orderItemDto.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu Item not found for ID: " + orderItemDto.getMenuItemId()));;

            //Set Menu Item Details for the Order Item
            orderItem.setMenuItemId(menuItem.getMenuItemId());
            orderItem.setMenuItemName(menuItem.getMenuItemName());

            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setUpdatedDate(currentDate);
            orderItem.setOrder(existingOrder);
            updatedOrderItems.add(orderItem);
            totalAmount += menuItem.getMenuItemPrice() * orderItemDto.getQuantity();
        }
        existingOrder.setTotalAmount(totalAmount);
        existingOrder.setOrderItemList(updatedOrderItems);

        List<OrderItem> savedUpdatedOrderItems = orderItemRepository.saveAll(updatedOrderItems);
        Order savedOrder = orderRepository.save(existingOrder);
        savedOrder.setOrderItemList(savedUpdatedOrderItems);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long orderId, Order order) {
        return null;
    }

    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).get();
    }
}
