package com.habibi.cafemanagement.service.order;

import com.habibi.cafemanagement.dto.order.OrderRequest;
import com.habibi.cafemanagement.dto.order.OrderResponse;
import com.habibi.cafemanagement.dto.common.SortRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse updateOrder(Long id, OrderRequest request);

    OrderResponse getOrderById(Long id);

    void deleteOrderById(Long id);

    Page<OrderResponse> getAllOrdersPage(int page, int size, List<SortRequest> sortObjects);
}
