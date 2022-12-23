alter table postgres.public.reviews
drop column  book_id;

alter table postgres.public.reviews
add column book_id bigint
references postgres.public.books (id)
on delete cascade
on UPDATE cascade;