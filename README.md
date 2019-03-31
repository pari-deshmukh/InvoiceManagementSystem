# InvoiceManagementSystem
Windows Neon &amp; Metro Style Java based Invoice Management System using MySQL as the DB server.

# Database Structure

DB Name: invoice

DB Credentials: `invadmin / invpassword`
```
MariaDB [invoice]> show tables;
+----------------------+
| Tables_in_invoice    |
+----------------------+
| inv_invoice_products |
| inv_invoices         |
| inv_products         |
| inv_users            |
+----------------------+
4 rows in set (0.001 sec)

MariaDB [invoice]> desc inv_users;
+--------+-------------+------+-----+---------+----------------+
| Field  | Type        | Null | Key | Default | Extra          |
+--------+-------------+------+-----+---------+----------------+
| uadmin | tinyint(1)  | NO   |     | 0       |                |
| upass  | varchar(50) | NO   |     | NULL    |                |
| uname  | varchar(50) | NO   |     | NULL    |                |
| uemail | varchar(50) | NO   |     | NULL    |                |
| uid    | int(11)     | NO   | PRI | NULL    | auto_increment |
+--------+-------------+------+-----+---------+----------------+
5 rows in set (0.001 sec)

MariaDB [invoice]> desc inv_products;
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| discount    | float        | YES  |     | NULL    |       |
| description | varchar(500) | YES  |     | NULL    |       |
| unit_price  | float        | NO   |     | NULL    |       |
| name        | varchar(100) | NO   |     | NULL    |       |
| ID          | int(11)      | NO   | PRI | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
5 rows in set (0.001 sec)

MariaDB [invoice]> desc inv_invoices;
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| change_amt  | float        | NO   |     | NULL    |       |
| cash        | float        | NO   |     | NULL    |       |
| discount    | float        | YES  |     | NULL    |       |
| service_tax | float        | NO   |     | NULL    |       |
| vat         | float        | NO   |     | NULL    |       |
| subtotal    | float        | NO   |     | NULL    |       |
| remarks     | varchar(500) | YES  |     | NULL    |       |
| inv_date    | date         | NO   |     | NULL    |       |
| customer    | varchar(250) | NO   |     | NULL    |       |
| id          | int(11)      | NO   | PRI | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
10 rows in set (0.001 sec)

MariaDB [invoice]> desc inv_invoice_products;
+------------+---------+------+-----+---------+-------+
| Field      | Type    | Null | Key | Default | Extra |
+------------+---------+------+-----+---------+-------+
| qty        | int(11) | NO   |     | NULL    |       |
| product_id | int(11) | NO   | MUL | NULL    |       |
| invoice_id | int(11) | NO   | MUL | NULL    |       |
+------------+---------+------+-----+---------+-------+
3 rows in set (0.001 sec)
```
