CREATE TABLE forum_post.forum_users(
    id bigserial primary key,
    username varchar(100) unique,
    password text,
    first_name varchar(100),
    last_name varchar(100),
    email text unique, --todo make custom type for email,
    role text,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
)