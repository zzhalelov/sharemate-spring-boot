create table users
(
    id    serial primary key,
    name  varchar(50) not null,
    email varchar(50) not null
);

create table items
(
    id           serial primary key,
    name         varchar(50)               not null,
    description  varchar(200)              not null,
    is_available boolean                   not null,
    owner_id     int references users (id) not null
);

create table bookings
(
    id         serial primary key,
    start_date timestamp without time zone not null,
    end_date   timestamp without time zone not null,
    item_id    int references items (id)   not null,
    booker_id  int references users (id)   not null,
    status     int                         not null
);

create table comments
(
    id         serial primary key,
    text       varchar(200)                not null,
    item_id    int references items (id)   not null,
    author_id  int references users (id)   not null,
    created_at timestamp without time zone not null
);