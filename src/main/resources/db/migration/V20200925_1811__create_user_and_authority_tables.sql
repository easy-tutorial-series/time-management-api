CREATE TABLE user
(
    id       INT                NOT NULL PRIMARY KEY auto_increment,
    name     VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100)       NOT NULL
);

CREATE TABLE authority
(
    user_id   INT         NOT NULL,
    authority VARCHAR(50) NOT NULL,
    UNIQUE KEY user_id (user_id, authority),
    CONSTRAINT authority_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id)
);

INSERT INTO `user` (`id`, `name`, `password`)
VALUES ('1', 'root', '$2a$10$IL3BZ4uf55GIZ4BUs0zVmeG1SpWcsJapzrHYRlnhimvoOUwRrdzPy'),
       ('2', 'admin', '$2a$10$ORZEKHgYJRu2.RxaU7raZejrOQD7Db4wtaWy2Q70aNleWOnruz0hS'),
       ('3', 'user', '$2a$10$kJxmFIR2gllyc2tU4DntAeGU269gDxNkQ3oOxou6TGVqmVoWg2INm');

INSERT INTO `authority`(`user_id`, `authority`)
VALUES ('1', 'ROLE_ADMIN'),
       ('1', 'ROLE_USER'),
       ('2', 'ROLE_ADMIN'),
       ('2', 'ROLE_USER'),
       ('3', 'ROLE_USER');
