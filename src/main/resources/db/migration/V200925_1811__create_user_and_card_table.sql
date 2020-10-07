create table user
(
    id       int                not null primary key auto_increment,
    name     varchar(50) unique not null,
    password varchar(100)       not null
);

create table authority
(
    user_id   int,
    authority varchar(50) not null,
    unique key user_id (user_id, authority),
    constraint authority_ibfk_1 foreign key (user_id) references user (id)
);
