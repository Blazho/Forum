CREATE TABLE forum_post.user_permissions(
    id bigserial primary key,
    user_id bigserial not null,
    permission_id bigserial not null,
    permission_layer varchar(15),
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint
)