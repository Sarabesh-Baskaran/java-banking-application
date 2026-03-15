create database bankdb;

use bankdb;

create table accounts(
    acc_no varchar(20) primary key,
    name varchar(100) not null,
    age int,
    mobile varchar(15),
    email varchar(100),
    balance double,
    pin char(4)
);