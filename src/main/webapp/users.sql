drop table if exists users;

create table if not exists users(
    username varchar(25) primary key,
    hash_password varchar(60) not null,
    firstName varchar(25) not null,
    lastName varchar(25) not null,
    email varchar(25) unique not null
    );

/*
Plain password to test login functionality

insert into users (username, hash_password, firstName, lastName, email) values
    ('user1', '123456', 'John', 'Doe', 'johndoe@email.com'),
    ('user2', '123456', 'Jane', 'Doe', 'janedoe@email.com'),
*/

select * from users;
