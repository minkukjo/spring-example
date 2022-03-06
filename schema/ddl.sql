CREATE SCHEMA test;

create table `user`
(
	id bigint auto_increment primary key,
	name varchar(20) not null,
	nickname varchar(30) not null,
	password varchar(100) not null,
	phone_number int not null,
	email varchar(100) not null,
	gender varchar(30) null,
	created_at datetime not null,
	updated_at datetime null,
	constraint user_email_uindex
		unique (email)
);

create table `order`
(
	id bigint auto_increment primary key,
	user_id bigint not null,
	order_number varchar(12) not null,
	product_name varchar(100) not null,
	payment_date datetime not null,
	created_at datetime not null,
	updated_at datetime null,
	constraint order_number_uindex
		unique (order_number),
	constraint order_user_id_fk
		foreign key (user_id) references user (id)
);
