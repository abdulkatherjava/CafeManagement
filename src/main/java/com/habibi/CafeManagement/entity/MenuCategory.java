package com.habibi.CafeManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu_category")
public class MenuCategory {
//    private static final long serialVersionUID = -2061391412917090402L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer CategoryId;

    @Column(name = "menu_item_category", nullable = false)
    private String menuCategory;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToMany( mappedBy = "menuCategory")
    private List<MenuItem> MenuItemList;

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
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

    @JsonManagedReference
    public List<MenuItem> getMenuItemList() {
        return MenuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        MenuItemList = menuItemList;
    }
}
