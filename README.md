# Java Banking Application

## 📌 Project Overview

This project is a simple Banking Application developed using **Java, JDBC, and MySQL**.
It allows users to perform basic banking operations through a **console-based interface** while securely storing data in a MySQL database.

The application demonstrates the use of **JDBC for database connectivity** and **SQL queries for managing banking transactions**.

---

##  Technologies Used

* Java
* JDBC (Java Database Connectivity)
* MySQL
* SQL

---

##  Features

* Create a new bank account
* User login using Account Number and PIN
* Deposit money
* Withdraw money
* Check account balance
* Data stored securely in MySQL database

---

##  Database Setup

1. Create a database:

```sql
CREATE DATABASE banking_system;
```

2. Create the accounts table:

```sql
CREATE TABLE accounts (
    acc_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    pin VARCHAR(10),
    balance DOUBLE
);
```

---

##  How to Run the Project

1. Clone the repository

```
git clone https://github.com/your-username/java-banking-application.git
```

2. Open the project in your IDE (VS Code / IntelliJ / Eclipse)

3. Configure the MySQL database connection in the Java code.

4. Run the main Java file.

---

##  Learning Outcome

This project helped in understanding:

* JDBC connectivity with MySQL
* SQL operations (INSERT, UPDATE, SELECT)
* Basic banking transaction logic
* Java console application development

---

##  Author

**Sarabesh**
Aspiring Backend Developer
