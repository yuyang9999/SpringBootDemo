# drop table if exists user_account_roles;
# drop table if exists profile_stocks;
# drop table if exists profiles;
# drop table if exists user_account;
# drop table if exists stock_history;
# drop table if exists stock_symbols;

create table if not EXISTS user_account (
  user_id int(11) not null auto_increment,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  enabled tinyint(4) NOT NULL DEFAULT 1,
  email varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_key_idx` (`username`)
) default character set utf8mb4;


create table if not EXISTS user_account_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `user_account` (`username`)
) default character set utf8mb4;


create table if not EXISTS stock_symbols (
  stock_id int(11) not null auto_increment,
  symbol varchar(50) not null,
  name varchar(100) not null,
  sector varchar(50),
  industry varchar(100),
  primary key (`stock_id`),
  unique key `stock_symbol` (`symbol`)
) default character set utf8mb4;

create table if not EXISTS stock_history(
  h_id int(11) not null auto_increment,
  symbol varchar(45) not null,
  date DATE not null,
  open FLOAT(10,2),
  high FLOAT(10, 2),
  low  FLOAT(10, 2),
  clos FLOAT(10,2),
  adj_close FLOAT(10,2),
  volume bigint(13),
  PRIMARY key (`h_id`),
  UNIQUE KEY `sh_unique` (`symbol`, `date`),
  CONSTRAINT `sh_symbol_id` FOREIGN KEY (`symbol`) REFERENCES `stock_symbols` (`symbol`)
) DEFAULT CHARACTER SET utf8mb4;


CREATE TABLE if not EXISTS profiles (
  pid int(11) not null auto_increment,
  user_id int(11) not null,
  pname varchar(45) not null,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `unique_pid_pname` (`user_id`, `pname`),
  KEY `profile_name_index` (`pid`, `pname`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`user_id`)
) default character set utf8mb4;

create table if not EXISTS profile_stocks (
  sid int(11) not null auto_increment,
  user_id int(11) not null,
  pid int(11) not null,
  sname varchar(45) not null,
  price FLOAT(11,2),
  share int(11),
  bought_time DATE,
  PRIMARY KEY (`sid`),
  KEY `stock_name_idx` (`pid`, `sname`),
  CONSTRAINT `fk_stock_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`user_id`),
  CONSTRAINT `fk_pid` FOREIGN KEY (`pid`) REFERENCES `profiles` (`pid`),
  CONSTRAINT `ps_fk_symbol` FOREIGN KEY (`sname`) REFERENCES `stock_symbols` (`symbol`)
) default character set utf8mb4;