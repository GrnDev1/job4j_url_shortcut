create table urls
(
    id        serial primary key not null,
    full_url  varchar(2000) unique,
    short_url varchar(2000) unique,
    total     int                not null,
    site_id   int references sites (id)
);