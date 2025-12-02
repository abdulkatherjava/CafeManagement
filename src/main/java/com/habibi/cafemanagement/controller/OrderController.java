package com.habibi.cafemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.habibi.cafemanagement.common.PageableUtil;
import com.habibi.cafemanagement.dto.common.PageAndSortRequest;
import com.habibi.cafemanagement.dto.common.PagedResponse;
import com.habibi.cafemanagement.dto.order.OrderRequest;
import com.habibi.cafemanagement.dto.order.OrderResponse;
import com.habibi.cafemanagement.service.order.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.updateOrder(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/list")
    public ResponseEntity<PagedResponse<OrderResponse>> getAllOrdersList(@RequestBody PageAndSortRequest request) {
        Page<OrderResponse> page = orderService.getAllOrdersPage(
                request.getPage(),
                request.getSize(),
                request.getSortObjects());
        PagedResponse<OrderResponse> resp = PageableUtil.toPagedResponse(page);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/orders")
    public ResponseEntity<PagedResponse<OrderResponse>> getAllOrdersQueryParams(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) List<String> sortParams) {

        var sortObjects = com.habibi.cafemanagement.common.PageableUtil.parseSortParams(sortParams);
        Page<OrderResponse> pageResult = orderService.getAllOrdersPage(page, size, sortObjects);
        PagedResponse<OrderResponse> resp = com.habibi.cafemanagement.common.PageableUtil.toPagedResponse(pageResult);
        return ResponseEntity.ok(resp);
    }
}
