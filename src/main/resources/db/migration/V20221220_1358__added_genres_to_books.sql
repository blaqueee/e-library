create table genres
(
    id   bigserial not null
        constraint genres_pkey
            primary key,
    name varchar(255)
);

alter table genres
    owner to postgres;



create table books_genres
(
    book_id   bigint not null
        constraint fklv42b6uemg63q27om39jjbt9o
            references books,
    genres_id bigint not null
        constraint fkd69nnd4vn4ql5pp5i0fmmr7de
            references genres,
    constraint books_genres_pkey
        primary key (book_id, genres_id)
);

alter table books_genres
    owner to postgres;

