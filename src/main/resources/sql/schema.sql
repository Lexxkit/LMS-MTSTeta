-- auto-generated definitiuon
create table roles
(
    id   bigserial
        constraint roles_pkey
            primary key,
    name varchar(255)
);

-- users
create table users
(
    id                  bigserial
        constraint users_pkey
            primary key,
    created_at          timestamp,
    created_by          varchar(255),
    updated_at          timestamp,
    updated_by          varchar(255),
    achievements        varchar(255),
    email               varchar(255)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name          varchar(255),
    last_name           varchar(255),
    password            varchar(255),
    phone               varchar(255),
    social_network_link varchar(255),
    username            varchar(255)
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

-- user roles
create table users_roles
(
    users_id bigint not null
        constraint fkml90kef4w2jy7oxyqv742tsfc
            references users,
    roles_id bigint not null
        constraint fka62j07k5mhgifpp955h37ponj
            references roles,
    constraint users_roles_pkey
        primary key (users_id, roles_id)
);

-- courses
create table courses (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        author varchar(255),
        avg_rating float8,
        description text,
        duration_weeks int4,
        tag varchar(255),
        title varchar(255),
        primary key (id)
    )
