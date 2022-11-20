drop table if exists oauth2_authorization;
drop table if exists oauth2_authorization_consent;
drop table if exists oauth2_registered_client;
drop table if exists authorities;
drop table if exists users;

create table oauth2_authorization
(
    id                            varchar(100) not null
        primary key,
    registered_client_id          varchar(100) not null,
    principal_name                varchar(200) not null,
    authorization_grant_type      varchar(100) not null,
    attributes                    blob null,
    state                         varchar(500) null,
    authorization_code_value      blob null,
    authorization_code_issued_at  timestamp null,
    authorization_code_expires_at timestamp null,
    authorization_code_metadata   blob null,
    access_token_value            blob null,
    access_token_issued_at        timestamp null,
    access_token_expires_at       timestamp null,
    access_token_metadata         blob null,
    access_token_type             varchar(100) null,
    access_token_scopes           varchar(1000) null,
    oidc_id_token_value           blob null,
    oidc_id_token_issued_at       timestamp null,
    oidc_id_token_expires_at      timestamp null,
    oidc_id_token_metadata        blob null,
    refresh_token_value           blob null,
    refresh_token_issued_at       timestamp null,
    refresh_token_expires_at      timestamp null,
    refresh_token_metadata        blob null
);

create table oauth2_authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

create table oauth2_registered_client
(
    id                            varchar(100)                        not null
        primary key,
    client_id                     varchar(100)                        not null,
    client_id_issued_at           timestamp default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(200) null,
    client_secret_expires_at      timestamp null,
    client_name                   varchar(200)                        not null,
    client_authentication_methods varchar(1000)                       not null,
    authorization_grant_types     varchar(1000)                       not null,
    redirect_uris                 varchar(1000) null,
    scopes                        varchar(1000)                       not null,
    client_settings               varchar(2000)                       not null,
    token_settings                varchar(2000)                       not null
);

create table users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  tinyint(1) not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint ix_auth_username
        unique (username, authority),
    constraint fk_authorities_users
        foreign key (username) references users (username)
);
