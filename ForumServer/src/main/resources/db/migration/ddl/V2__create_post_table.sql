CREATE TABLE forum_post.posts(
    id bigserial primary key,
    html text,
    thread bigserial,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
)