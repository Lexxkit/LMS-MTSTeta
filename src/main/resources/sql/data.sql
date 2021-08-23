insert into roles(name)
values ('ROLE_OWNER'),
       ('ROLE_ADMIN'),
       ('ROLE_TUTOR'),
       ('ROLE_STUDENT');

-- users
insert into users (username, password, first_name, last_name, email, phone)
values ('fdunderdale0', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Francoise',
        'Dunderdale', 'fdunderdale0@economist.com', '4669148983');
insert into users (username, password, first_name, last_name, email, phone)
values ('iswindells1', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Ingeborg',
        'Swindells', 'iswindells1@mediafire.com', '7425762528');
insert into users (username, password, first_name, last_name, email, phone)
values ('fpleager2', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Flory',
        'Pleager', 'fpleager2@wisc.edu', '1492496944');
insert into users (username, password, first_name, last_name, email, phone)
values ('bmcallister3', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Barn',
        'McAllister', 'bmcallister3@flickr.com', '9671784483');
insert into users (username, password, first_name, last_name, email, phone)
values ('tpohlak4', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Talya',
        'Pohlak', 'tpohlak4@accuweather.com', '1671330060');
insert into users (username, password, first_name, last_name, email, phone)
values ('scurwood5', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Shaine',
        'Curwood', 'scurwood5@economist.com', '3838785530');
insert into users (username, password, first_name, last_name, email, phone)
values ('ayaldren6', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Abraham',
        'Yaldren', 'ayaldren6@purevolume.com', '3748989759');
insert into users (username, password, first_name, last_name, email, phone)
values ('mandriolli7', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Matthus',
        'Andriolli', 'mandriolli7@spotify.com', '2845657554');
insert into users (username, password, first_name, last_name, email, phone)
values ('hsievewright8', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Heinrik',
        'Sievewright', 'hsievewright8@amazonaws.com', '3321891837');
insert into users (username, password, first_name, last_name, email, phone)
values ('temp', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2', 'Alfonso', 'Webbe',
        'awebbe9@angelfire.com', '1779892981');
-- owner, password: 123
insert into users (username, password)
values ('owner', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2');
-- admin, password: 123
insert into users (username, password)
values ('admin', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2');
-- tutor, password: 123
insert into users (username, password)
values ('tutor', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2');
-- student, password: 123
insert into users (username, password)
values ('student', '$2y$10$m1PjtvPCy4C5Pa88EVFkd..YZWrpDxsc85jZfCx4Uos3.zPtVzbs2');

insert into users_roles (users_id, roles_id)
values (1, '4');
insert into users_roles (users_id, roles_id)
values (2, '4');
insert into users_roles (users_id, roles_id)
values (3, '4');
insert into users_roles (users_id, roles_id)
values (4, '4');
insert into users_roles (users_id, roles_id)
values (5, '4');
insert into users_roles (users_id, roles_id)
values (6, '4');
insert into users_roles (users_id, roles_id)
values (7, '4');
insert into users_roles (users_id, roles_id)
values (8, '4');
insert into users_roles (users_id, roles_id)
values (9, '4');
insert into users_roles (users_id, roles_id)
values (10, '4');
insert into users_roles (users_id, roles_id)
values (11, '1');
insert into users_roles (users_id, roles_id)
values (12, '2');
insert into users_roles (users_id, roles_id)
values (13, '3');
insert into users_roles (users_id, roles_id)
values (14, '4');

-- courses
insert into courses (author, title)
values ('Linnell Girsch', 'Seamless optimizing collaboration');
insert into courses (author, title)
values ('Nettle Bentson', 'Cloned disintermediate projection');
insert into courses (author, title)
values ('Egor Coath', 'User-centric real-time intranet');
insert into courses (author, title)
values ('Norman Bowton', 'Public-key background application');
insert into courses (author, title)
values ('Loraine Danielis', 'User-friendly tangible focus group');
insert into courses (author, title)
values ('Kliment Read', 'Distributed foreground orchestration');
insert into courses (author, title)
values ('Kalil Ribchester', 'User-centric methodical toolset');
insert into courses (author, title)
values ('Joscelin Savin', 'Sharable hybrid capacity');
insert into courses (author, title)
values ('Romonda Edinburgh', 'Configurable didactic attitude');
insert into courses (author, title)
values ('Ailene Fuggle', 'Extended object-oriented time-frame');
