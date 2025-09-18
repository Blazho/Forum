CREATE TABLE forum_post.posts(
    id bigserial primary key,
    html text,
    thread bigserial NOT NULL,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
);

ALTER TABLE forum_post.posts
    ADD CONSTRAINT fk_users_created_by
        FOREIGN KEY (created_by) REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_users_last_modified_by
        FOREIGN KEY (last_modified_by) REFERENCES forum_post.forum_users(id),
    ADD CONSTRAINT fk_thread
        FOREIGN KEY (thread) REFERENCES forum_post.threads(id);