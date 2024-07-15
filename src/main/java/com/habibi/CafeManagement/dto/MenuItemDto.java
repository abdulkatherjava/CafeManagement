package com.habibi.CafeManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

import static java.lang.Boolean.TRUE;

public class MenuItemDto {

    private Integer id;

    @NotEmpty(message = "Item name is required")
    @NotNull(message = "Item name is required")
    private String menuItemName;

    private String menuItemDescription;

    @NotNull(message = "Item price is required")
    @Positive( message = "Enter the valid price")
    private Long menuItemPrice;


    @NotNull(message = "Item Category is required")
    private Integer menuItemCategoryId;

    @NotEmpty(message = "Item source path is required")
    @NotNull(message = "Item source path is required")
    private String menuItemSrcPath;

    private String barCode;

    private String itemQuantity;

    private MenuCategoryDto menuCategoryDto;

    private Date createdDate;

    private Date updatedDate;

    @NotNull(message = "availability is required")
    private Boolean isAvailable = TRUE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public void setMenuItemDescription(String menuItemDescription) {
        this.menuItemDescription = menuItemDescription;
    }

    public Long getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(Long menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }


    public MenuCategoryDto getMenuCategoryDto() {
        return menuCategoryDto;
    }

    public void setMenuCategoryDto(MenuCategoryDto menuCategoryDto) {
        this.menuCategoryDto = menuCategoryDto;
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

    public Integer getMenuItemCategoryId() {
        return menuItemCategoryId;
    }

    public void setMenuItemCategoryId(Integer menuItemCategoryId) {
        this.menuItemCategoryId = menuItemCategoryId;
    }

    public String getMenuItemSrcPath() {
        return menuItemSrcPath;
    }

    public void setMenuItemSrcPath(String menuItemSrcPath) {
        this.menuItemSrcPath = menuItemSrcPath;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
