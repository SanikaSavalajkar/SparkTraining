create database projectManagementSystem;
use projectManagementSystem;

/*Schema Design*/

create table project (
project_id int primary key auto_increment,
project_name varchar(100) not null,
project_description varchar(200) not null,
start_date date not null,
status enum('started', 'dev', 'build', 'test', 'deployed') not null
);

create table employee(
employee_id int primary key auto_increment,
employee_name varchar(100) not null,
employee_designation varchar(50) not null,
employee_gender varchar(20) not null,
employee_salary float,
project_id int,
foreign key(project_id) references project(project_id)
);

create table task(
task_id int primary key auto_increment,
task_name varchar(100) not null,
project_id int,
employee_id int,
status enum('assigned', 'started', 'completed'),
foreign key(project_id) references project(project_id),
foreign key(employee_id) references employee(employee_id)
);
