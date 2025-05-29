CREATE TABLE forum_post.threads(
    id bigserial primary key,
    title varchar(50) not null ,
    status varchar(10),
    parent_thread bigint,
    description varchar(200) not null,
    date_created timestamptz,
    last_date_modified timestamptz,
    created_by bigint,
    last_modified_by bigint
)