CREATE TABLE forum_post.permissions(
    id bigserial primary key,
    title varchar(50) not null,
    description varchar(256) not null,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
);

ALTER TABLE forum_post.permissions
    ADD CONSTRAINT fk_users_created_by
        FOREIGN KEY (created_by) REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_users_last_modified_by
        FOREIGN KEY (last_modified_by) REFERENCES forum_post.forum_users(id);