create table product
(
    id                 uuid primary key,
    name               varchar(255)             not null,
    description        text                     not null,
    version            bigint                   not null,
    created_date       timestamp with time zone not null,
    last_modified_date timestamp with time zone not null
);
