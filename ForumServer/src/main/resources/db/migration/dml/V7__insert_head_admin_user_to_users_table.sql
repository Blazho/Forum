INSERT INTO forum_post.forum_users(
	username, password, first_name, last_name, email, role, date_created, last_date_modified, created_by, last_modified_by)
	VALUES ('forumadmin', '$2a$10$6IneZusNAsfOmhjc/SEdHe79i95QKReqaHn4lWe6JNt07DH5XPi1S', 'head', 'admin', 'forumadmin@test.com', 'ROLE_HEAD_ADMIN', now(), now(), null, null);


INSERT INTO forum_post.user_permissions(
	user_id, permission_id, permission_layer, date_created, last_date_modified, created_by, last_modified_by)
	VALUES ((select id from forum_post.forum_users where username = 'forumadmin'),
	 (select id from forum_post.permissions where title = 'PROMOTE_USER_PERMISSION'), 'EDIT', now(), now(), null, null),
	 ((select id from forum_post.forum_users where username = 'forumadmin'),
     (select id from forum_post.permissions where title = 'POST_PERMISSION'), 'EDIT', now(), now(), null, null),
     ((select id from forum_post.forum_users where username = 'forumadmin'),
     (select id from forum_post.permissions where title = 'THREAD_1_PERMISSION'), 'EDIT', now(), now(), null, null),
     ((select id from forum_post.forum_users where username = 'forumadmin'),
     (select id from forum_post.permissions where title = 'THREAD_2_PERMISSION'), 'EDIT', now(), now(), null, null);
