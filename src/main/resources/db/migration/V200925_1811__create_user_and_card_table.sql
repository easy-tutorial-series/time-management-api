create table `user`
(
    `id`       int                not null auto_increment primary key,
    `name`     varchar(64) unique not null,
    `password` varbinary(64)      not null
);

insert into `user` (name, password)
values ('admin', 'password'),
       ('root', 'password'),
       ('user1', 'password');

create table `card`
(
    `id`      int not null auto_increment primary key,
    `user_id` int not null,
    `content` varchar(256),
    foreign key (`user_id`) references `user` (`id`)
);

insert into `card` (user_id, content)
values (1, 'practice'),
       (1, 'sleep'),
       (1, 'say hello'),
       (2, 'practice'),
       (2, 'sleep'),
       (2, 'say hello'),
       (3, 'practice'),
       (3, 'sleep'),
       (3, 'say hello');
