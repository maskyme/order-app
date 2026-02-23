
CREATE TABLE users (
  id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE dish (
  dish_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  available BOOLEAN NOT NULL,
  deleted BOOLEAN NOT NULL
);

CREATE TABLE orders (
  order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  client_id BIGINT,
  order_date TIMESTAMP,
  status VARCHAR(50),
  created_at TIMESTAMP
);

CREATE TABLE order_items (
  id_order_item BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT,
  dish_id BIGINT,
  quantity INT
);

ALTER TABLE orders ADD CONSTRAINT FK_orders_client FOREIGN KEY (client_id) REFERENCES users(id_user);
ALTER TABLE order_items ADD CONSTRAINT FK_orderitems_order FOREIGN KEY (order_id) REFERENCES orders(order_id);
ALTER TABLE order_items ADD CONSTRAINT FK_orderitems_dish FOREIGN KEY (dish_id) REFERENCES dish(dish_id);
