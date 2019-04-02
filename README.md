# InvoiceManagementSystem
Windows Neon &amp; Metro Style Java based Invoice Management System using MySQL as the DB server.

# Database Structure

DB Name: invoice

DB Credentials: `invadmin / invpassword`
```
CREATE TABLE inv_users(uid int(11) AUTO_INCREMENT NOT NULL PRIMARY KEY, uname VARCHAR(250) NOT NULL, uemail VARCHAR(150) NOT NULL, upass VARCHAR(150) NOT NULL, uadmin BOOLEAN DEFAULT 0 NOT NULL);
Query OK, 0 rows affected (0.028 sec)

MariaDB [invoice]> CREATE TABLE inv_invoices(id int(11) AUTO_INCREMENT NOT NULL PRIMARY KEY, customer VARCHAR(250) NOT NULL, customer_contact_number VARCHAR(15), remarks VARCHAR(500), inv_date DATE NOT NULL, subtotal FLOAT NOT NULL, vat FLOAT NOT NULL, service_tax FLOAT NOT NULL, inv_amt FLOAT NOT NULL);
Query OK, 0 rows affected (0.018 sec)

MariaDB [invoice]> CREATE TABLE inv_products(id int(11) AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(250) NOT NULL, description VARCHAR(500), discount FLOAT, unit_price FLOAT NOT NULL);
Query OK, 0 rows affected (0.032 sec)

MariaDB [invoice]> CREATE TABLE inv_invoice_products(qty INT(11) NOT NULL, product_id INT(11) NOT NULL, invoice_id INT(11) NOT NULL, FOREIGN KEY(invoice_id) REFERENCES inv_invoices(id) ON DELETE CASCADE, FOREIGN KEY(product_id) REFERENCES inv_products(id));
Query OK, 0 rows affected (0.037 sec)

MariaDB [invoice]> SHOW TABLES;
+----------------------+
| Tables_in_invoice    |
+----------------------+
| inv_invoice_products |
| inv_invoices         |
| inv_products         |
| inv_users            |
+----------------------+
4 rows in set (0.001 sec)

MariaDB [invoice]> DESC inv_invoice_products;
+------------+---------+------+-----+---------+-------+
| Field      | Type    | Null | Key | Default | Extra |
+------------+---------+------+-----+---------+-------+
| qty        | int(11) | NO   |     | NULL    |       |
| product_id | int(11) | NO   | MUL | NULL    |       |
| invoice_id | int(11) | NO   | MUL | NULL    |       |
+------------+---------+------+-----+---------+-------+
3 rows in set (0.004 sec)

MariaDB [invoice]> DESC inv_invoices;
+-------------------------+--------------+------+-----+---------+----------------+
| Field                   | Type         | Null | Key | Default | Extra          |
+-------------------------+--------------+------+-----+---------+----------------+
| id                      | int(11)      | NO   | PRI | NULL    | auto_increment |
| customer                | varchar(250) | NO   |     | NULL    |                |
| customer_contact_number | varchar(15)  | YES  |     | NULL    |                |
| remarks                 | varchar(500) | YES  |     | NULL    |                |
| inv_date                | date         | NO   |     | NULL    |                |
| subtotal                | float        | NO   |     | NULL    |                |
| vat                     | float        | NO   |     | NULL    |                |
| service_tax             | float        | NO   |     | NULL    |                |
| inv_amt                 | float        | NO   |     | NULL    |                |
+-------------------------+--------------+------+-----+---------+----------------+
9 rows in set (0.002 sec)

MariaDB [invoice]> DESC inv_products;
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
| name        | varchar(250) | NO   |     | NULL    |                |
| description | varchar(500) | YES  |     | NULL    |                |
| discount    | float        | YES  |     | NULL    |                |
| unit_price  | float        | NO   |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
5 rows in set (0.003 sec)

MariaDB [invoice]> DESC inv_users;
+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| uid    | int(11)      | NO   | PRI | NULL    | auto_increment |
| uname  | varchar(250) | NO   |     | NULL    |                |
| uemail | varchar(150) | NO   |     | NULL    |                |
| upass  | varchar(150) | NO   |     | NULL    |                |
| uadmin | tinyint(1)   | NO   |     | 0       |                |
+--------+--------------+------+-----+---------+----------------+
5 rows in set (0.002 sec)
```
