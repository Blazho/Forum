CREATE TABLE forum_post.threads(
    id bigserial primary key,
    title varchar(50) not null ,
    status varchar(10),
    parent_thread bigint,
    description varchar(200) not null,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
);

ALTER TABLE forum_post.threads
    ADD CONSTRAINT fk_users_created_by
        FOREIGN KEY (created_by) REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_users_last_modified_by
        FOREIGN KEY (last_modified_by) REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_parent_thread
        FOREIGN KEY (parent_thread) REFERENCES forum_post.threads(id);