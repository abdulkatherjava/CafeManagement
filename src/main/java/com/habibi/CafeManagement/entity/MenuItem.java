package com.habibi.CafeManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "menu_item")
public class MenuItem {

//    private static final long serialVersionUID = -7318325800211188380L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "menu_item_name", nullable = false)
    private String menuItemName;

    @Column(name = "menu_item_description")
    private String menuItemDescription;

    @Column(name = "menu_item_price", nullable = false)
    private Long menuItemPrice;

    @Column(name = "menu_item_src_path")
    private String menuItemSrcPath;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "item_quantity")
    private String itemQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_category_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MenuCategory menuCategory;

    public MenuItem(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public MenuItem() {
    }

    public MenuItem(String menuItemName, String menuItemDescription, Long menuItemPrice, MenuCategory menuCategory, Date createdDate, Date updatedDate) {
        this.menuItemName = menuItemName;
        this.menuItemDescription = menuItemDescription;
        this.menuItemPrice = menuItemPrice;
        this.menuCategory = menuCategory;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    @JsonBackReference
    public MenuCategory getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
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
