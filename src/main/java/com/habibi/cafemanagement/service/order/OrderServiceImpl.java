package com.habibi.cafemanagement.service.order;

import com.habibi.cafemanagement.common.PageableUtil;
import com.habibi.cafemanagement.dto.common.SortRequest;
import com.habibi.cafemanagement.dto.order.OrderRequest;
import com.habibi.cafemanagement.dto.order.OrderResponse;
import com.habibi.cafemanagement.exception.ResourceNotFoundException;
import com.habibi.cafemanagement.model.Order;
import com.habibi.cafemanagement.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderDate(request.getOrderDate());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus(request.getOrderStatus());
        order.setMode(request.getMode());
        order.setComments(request.getComments());

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        order.setOrderDate(request.getOrderDate());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus(request.getOrderStatus());
        order.setMode(request.getMode());
        order.setComments(request.getComments());

        Order updated = orderRepository.save(order);
        return mapToResponse(updated);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    @Transactional
    @Override
    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    @Override
    public Page<OrderResponse> getAllOrdersPage(int page, int size, List<SortRequest> sortObjects) {
        Pageable pageable = PageableUtil.toPageable(page, size, sortObjects);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(this::mapToResponse);
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setOrderDate(order.getOrderDate());
        resp.setTotalAmount(order.getTotalAmount());
        resp.setOrderStatus(order.getOrderStatus());
        resp.setMode(order.getMode());
        resp.setComments(order.getComments());
        resp.setCreatedAt(order.getCreatedAt());
        resp.setUpdatedAt(order.getUpdatedAt());
        return resp;
    }
}
