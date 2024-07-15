CREATE TABLE menucategory (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    menu_item_category VARCHAR(50) NOT NULL,
    created_date DATETIME,
    updated_date DATETIME
);

CREATE TABLE menuitem (
    menu_item_id INT AUTO_INCREMENT PRIMARY KEY,
    menu_item_name VARCHAR(100) NOT NULL,
    menu_item_description TEXT,
    menu_item_price DECIMAL(10, 2) NOT NULL,
    menu_item_category_id INT NOT NULL,
    menu_item_src_path VARCHAR(255) NOT NULL,
    menu_item_src_path VARCHAR(255) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE;
    FOREIGN KEY (menu_item_category_id) REFERENCES menu_category(category_id),
    created_date DATETIME,
    updated_date DATETIME
);

CREATE TABLE purchaseorder (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_mode VARCHAR(20) NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    comments VARCHAR(255),
    created_date DATETIME,
    updated_date DATETIME
);

CREATE TABLE orderitem (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    created_date DATETIME,
    updated_date DATETIME,
    FOREIGN KEY (order_id) REFERENCES purchaseorder(order_id),
    FOREIGN KEY (menu_item_id) REFERENCES menuitem(menu_item_id)
);

CREATE TABLE delivery (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    is_deliver BOOLEAN NOT NULL, -- Boolean to indicate if it's a delivery (true) or not (false)
    delivery_charge DECIMAL(10, 2) NOT NULL, -- Fixed delivery charge
    order_id INT NOT NULL,-- Reference to the Order table
    created_date DATETIME,
    updated_date DATETIME
    FOREIGN KEY (order_id) REFERENCES Order(order_id)
);

CREATE TABLE payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_amount DECIMAL(10, 2) NOT NULL,
    payment_date DATETIME NOT NULL,
    created_date DATETIME,
    updated_date DATETIME
    FOREIGN KEY (Order_ID) REFERENCES Order(order_id)
);