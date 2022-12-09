create table telegram_user
(
    uuid       uuid
        constraint telegram_user_pk
            primary key,
    id         bigint,
    is_bot     bool,
    first_name varchar(45),
    last_name  varchar(45),
    username   varchar(45),
    type       varchar(15) default 'unknown'
);

create table telegram_result
(
    from_uuid  uuid
        constraint telegram_result_telegram_user_uuid_fk
            references telegram_user,
    chat_uuid  uuid
        constraint telegram_result_telegram_user_uuid_fk2
            references telegram_user,
    uuid       uuid
        constraint telegram_result_pk
            primary key,
    message_id varchar(15),
    date       bigint,
    text       varchar(45)
);

create table message
(
    uuid    uuid
        constraint message_pk
            primary key,
    message varchar(90),
    chat_id bigint
);

create table publish_event
(
    uuid               uuid
        constraint publish_event_pk
            primary key,
    message_id         uuid
        constraint publish_event_message_id_fk
            references message,
    telegram_result_id uuid
        constraint publish_event_telegram_result_id_fk
            references telegram_result,
    state              varchar(15)
);

