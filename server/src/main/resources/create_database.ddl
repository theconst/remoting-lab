drop database if exists enterprise;
create DATABASE enterprise;

use enterprise;

create table employee (
  id    int(10) not null auto_increment,
  name           varchar(64) not null,
  surname        varchar(64) not null,
  since          timestamp not null,
  occupation     varchar(16) not null,
  post           varchar(64) not null,
  primary key (id));


create table employee_shift_record (
  id int(10) not null auto_increment,
  comment varchar(32) not null,
  worked_from        timestamp not null,
  worked_to          timestamp not null,
  was_controlled     tinyint(1) not null,
  employee_id        int(10) not null,
  primary key (id),
  foreign key (employee_id) references employee(id)
);
