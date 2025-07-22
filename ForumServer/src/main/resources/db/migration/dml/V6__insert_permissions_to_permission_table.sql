INSERT INTO forum_post.permissions(title, description, date_created, last_date_modified, created_by, last_modified_by)
	VALUES
        ('PROMOTE_USER_PERMISSION', 'Permission to promote or demote user role', now(), now(), null, null),
        ('POST_PERMISSION', 'Permission to manage posts', now(), now(), null, null),
        ('THREAD_PARENT_PERMISSION', 'Permission to manage parent threads', now(), now(), null, null),
        ('THREAD_CHILD_PERMISSION', 'Permission to manage child threads', now(), now(), null, null);