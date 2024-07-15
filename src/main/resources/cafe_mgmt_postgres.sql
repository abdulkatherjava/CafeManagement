-- Creating schema "habibi" if needed
CREATE SCHEMA IF NOT EXISTS cafe_mgmt;

-- Use the schema
SET search_path TO cafe_mgmt;

-- Table: menu_category
CREATE TABLE menu_category (
    category_id SERIAL PRIMARY KEY,
    menu_item_category VARCHAR(50) NOT NULL,
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

-- Table: menu_item
CREATE TABLE menu_item (
    menu_item_id SERIAL PRIMARY KEY,
    menu_item_name VARCHAR(100) NOT NULL,
    bar_code VARCHAR(100),
    item_quantity VARCHAR(10),
    menu_item_description TEXT,
    menu_item_price DECIMAL(10, 2) NOT NULL,
    menu_item_category_id INT NOT NULL,
    menu_item_src_path VARCHAR(255) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    FOREIGN KEY (menu_item_category_id) REFERENCES menu_category(category_id)
);

-- Table: purchase_order
CREATE TABLE purchase_order (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_mode VARCHAR(20) NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    comments VARCHAR(255),
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

-- Table: order_item
CREATE TABLE order_item (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    menu_item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES purchase_order(order_id)
);

-- Table: delivery_tracker
CREATE TABLE delivery_tracker (
    delivery_tracker_id SERIAL PRIMARY KEY,
    is_delivered BOOLEAN NOT NULL,
    delivery_charge DECIMAL(10, 2) NOT NULL,
    order_id INT NOT NULL,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES purchase_order(order_id)
);

-- Table: payment


-- Restart Identity query
TRUNCATE TABLE menu_category RESTART IDENTITY CASCADE;
TRUNCATE TABLE menu_item RESTART IDENTITY CASCADE;
TRUNCATE TABLE order_item RESTART IDENTITY CASCADE;
TRUNCATE TABLE purchase_order RESTART IDENTITY CASCADE;

