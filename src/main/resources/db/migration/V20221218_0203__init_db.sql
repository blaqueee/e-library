create table activation_tokens
(
    id         bigserial    not null
        constraint activation_tokens_pkey
            primary key,
    created_at timestamp    not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    token      varchar(255) not null,
    username   varchar(255) not null
);

alter table activation_tokens
    owner to postgres;

create table users
(
    id       bigserial    not null
        constraint users_pkey
            primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null,
    username varchar(255) not null
);

alter table users
    owner to postgres;

create table books
(
    id          bigserial     not null
        constraint books_pkey
            primary key,
    author      varchar(255)  not null,
    book_url    varchar(255),
    created_at  timestamp     not null,
    description varchar(1000) not null,
    image_url   varchar(255),
    name        varchar(255)  not null,
    year        integer       not null,
    creator_id  bigint
        constraint fkmxncjvvv7e187xdc3bldwdug4
            references users
);

alter table books
    owner to postgres;

create table bookmarks
(
    id      bigserial not null
        constraint bookmarks_pkey
            primary key,
    book_id bigint
        constraint fkevg2kut22i380wdyfpgmsl6kq
            references books,
    user_id bigint
        constraint fkdbsho2e05w5r13fkjqfjmge5f
            references users
);

alter table bookmarks
    owner to postgres;

create table password_reset_tokens
(
    id         bigserial    not null
        constraint password_reset_tokens_pkey
            primary key,
    created_at timestamp    not null,
    token      varchar(255) not null,
    user_id    bigint
        constraint fkk3ndxg5xp6v7wd4gjyusp15gq
            references users
);

alter table password_reset_tokens
    owner to postgres;

create table reviews
(
    id             bigserial not null
        constraint reviews_pkey
            primary key,
    comment        varchar(500),
    created_at     timestamp not null,
    rating         integer   not null,
    book_id        bigint
        constraint fk6a9k6xvev80se5rreqvuqr7f9
            references books,
    commentator_id bigint
        constraint fkh30f3y874ny4ss75y8t1ecx6d
            references users
);

alter table reviews
    owner to postgres;