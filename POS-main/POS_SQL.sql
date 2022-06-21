CREATE TABLE accounts (
   m_id VARCHAR(20) NOT NULL PRIMARY KEY,
   m_pw VARCHAR(20) NOT NULL,
   m_name VARCHAR(20) NOT NULL,
   m_position VARCHAR(20) NOT NULL,
   registry_date DATETIME NOT NULL,
   access_date DATETIME NOT NULL);
   
CREATE TABLE products (
   p_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   p_name VARCHAR(20) NOT NULL,
   p_price INT NOT NULL DEFAULT 0);
   
INSERT INTO products VALUES (NULL, '아메리카노', 2500);
INSERT INTO products VALUES (NULL, '라떼', 4000);
INSERT INTO products VALUES (NULL, '콜드브루', 5000);
INSERT INTO products VALUES (NULL, '초코라떼', 4500);
INSERT INTO products VALUES (NULL, '청귤티', 4500);
INSERT INTO products VALUES (NULL, '딸기라떼', 5000);
INSERT INTO products VALUES (NULL, '치즈케이크', 4000);
INSERT INTO products VALUES (NULL, '티라미수', 4000);
INSERT INTO products VALUES (NULL, '앙스콘', 3000);

CREATE TABLE orders (
   o_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   o_date DATETIME NOT NULL,
   m_id VARCHAR(20) NOT NULL,
   o_price INT NOT NULL DEFAULT 0,
   payType VARCHAR(20) NOT NULL,
   FOREIGN KEY(m_id) REFERENCES accounts(m_id));
   
CREATE TABLE order_detail (
   od_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
   o_id INT NOT NULL,
   p_id INT NOT NULL,
   o_quantity INT NOT NULL DEFAULT 0,
   p_price INT NOT NULL DEFAULT 0,
   FOREIGN KEY(o_id) REFERENCES orders(o_id),
   FOREIGN KEY(p_id) REFERENCES products(p_id));
