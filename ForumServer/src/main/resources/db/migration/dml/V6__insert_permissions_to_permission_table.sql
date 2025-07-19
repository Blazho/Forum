INSERT INTO forum_post.permissions(title, description, date_created, last_date_modified, created_by, last_modified_by)
	VALUES
        ('PROMOTE_USER_PERMISSION', 'Permission to promote or demote user role', now(), now(), null, null),
        ('POST_PERMISSION', 'Permission to manage posts', now(), now(), null, null),
        ('THREAD_1_PERMISSION', 'Permission to manage threads on level 1', now(), now(), null, null),
        ('THREAD_2_PERMISSION', 'Permission to manage threads on level 2', now(), now(), null, null);