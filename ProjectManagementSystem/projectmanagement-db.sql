create database projectmanagementsystem;
use projectmanagementsystem;

create table project (
    project_id int primary key auto_increment,
    project_name varchar(100),
    project_description varchar(255),
    start_date date,
    status varchar(50)
);

create table employee (
    employee_id int primary key auto_increment,
    employee_name varchar(100),
    employee_designation varchar(100),
    employee_gender varchar(10),
    employee_salary double,
    project_id int,
    hire_date date,
    foreign key (project_id) references project(project_id)
);

create table task (
    task_id int primary key auto_increment,
    task_name varchar(100),
    project_id int,
    employee_id int,
    status varchar(20),
    allocation_date date,
    deadline date,
    foreign key (project_id) references project(project_id),
    foreign key (employee_id) references employee(employee_id)
);

create table expense (
    expense_id int primary key auto_increment,
    employee_id int,
    amount double,
    expense_date date,
    foreign key (employee_id) references employee(employee_id)
);
