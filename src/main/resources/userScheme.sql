drop table if exists user_account_roles;
drop table if exists user_account;

create table user_account (
  user_id int(11) not null auto_increment,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  enabled tinyint(4) NOT NULL DEFAULT '1',
  email varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `name_key_idx` (`username`)
);

create table user_account_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `user_account` (`username`)
);

