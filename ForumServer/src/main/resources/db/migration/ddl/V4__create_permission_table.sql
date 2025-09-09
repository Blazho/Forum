CREATE TABLE forum_post.permissions(
    id bigserial primary key,
    title varchar(50) not null,
    description varchar(256) not null,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint,
    entity_status varchar(50)
)