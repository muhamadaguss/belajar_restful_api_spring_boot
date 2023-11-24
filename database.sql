CREATE TABLE users (
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) not null,
    name                varchar(100) not null,
    token               varchar(100),
    token_expired_at    BIGINT,
    primary key (username),
    unique (token)
    );

SELECT * FROM users;

Create table contacts (
    id              varchar(100) not null,
    username       varchar(100) not null,
    first_name      varchar(100) not null,
    last_name       varchar(100),
    email           varchar(100),
    phone           varchar(100),
    primary key (id),
    constraint fk_user_contacts
                      FOREIGN KEY (username)
                      REFERENCES users(username)
);

Select * from contacts;

CREATE table addresses (
    id              varchar(100) not null ,
    contact_id      varchar(100) not null ,
    street          varchar(200),
    city            varchar(100),
    province        varchar(100),
    country         varchar(100) not null,
    postal_code     varchar(10),
    primary key (id),
    constraint fk_contact_addresses
                       FOREIGN KEY (contact_id)
                       REFERENCES contacts(id)
)

select * from addresses;

delete from addresses;

delete from contacts;

delete from users;