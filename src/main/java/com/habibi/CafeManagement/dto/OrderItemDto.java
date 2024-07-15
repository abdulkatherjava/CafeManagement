package com.habibi.CafeManagement.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class OrderItemDto {

    @NotNull(message = "Choose the Items")
    private Integer menuItemId;

    @NotNull(message = "Choose the Quantity")
    private Integer quantity;

    private String menuItemName;

    private Date createdDate;

    private Date updatedDate;

    private OrderDto orderDto;

    private MenuItemDto menuItemDto;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public MenuItemDto getMenuItemDto() {
        return menuItemDto;
    }

    public void setMenuItemDto(MenuItemDto menuItemDto) {
        this.menuItemDto = menuItemDto;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}
