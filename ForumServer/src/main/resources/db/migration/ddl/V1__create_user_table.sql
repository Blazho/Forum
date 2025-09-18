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
);

ALTER TABLE forum_post.forum_users
    ADD CONSTRAINT fk_users_created_by
        FOREIGN KEY (created_by)
        REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_users_last_modified_by
        FOREIGN KEY (last_modified_by)
        REFERENCES forum_post.forum_users(id);