DROP PROCEDURE IF EXISTS add_test_users();
DROP PROCEDURE if EXISTS add_test_user_and_user_permissions(VARCHAR, VARCHAR, VARCHAR, VARCHAR[], VARCHAR[]);

CREATE OR REPLACE PROCEDURE add_test_users()
LANGUAGE plpgsql
AS $$
DECLARE
    v_permission_titles TEXT[];
    v_permission_layers TEXT[] := ARRAY['NONE', 'VIEW', 'CREATE', 'DELETE', 'EDIT'];
BEGIN
    SELECT array_agg(title) INTO v_permission_titles FROM forum_post.permissions;

    FOR i IN 1..array_length(v_permission_layers, 1) LOOP
        CALL add_test_user_and_user_permissions(
            'testuser_' || i,
            'testuser_' || v_permission_layers[i],
            'hasperm',
            v_permission_titles,
            v_permission_layers[i]
        );
    END LOOP;
END;
$$;

CREATE OR REPLACE PROCEDURE add_test_user_and_user_permissions(
    p_username   VARCHAR,
    p_user_fname   VARCHAR,
    p_user_lname   VARCHAR,
    p_permissions  VARCHAR[],
    p_perm_layers  VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    IF p_permissions IS NULL OR array_length(p_permissions, 1) IS NULL THEN
        RAISE EXCEPTION 'Permissions and Permission Layers cannot be null';
    END IF;

    INSERT INTO forum_post.forum_users
        (username, password, first_name, last_name, email, role,
         date_created, last_date_modified, created_by, last_modified_by, entity_status)
    VALUES
        (p_username,
         crypt('Pass1234', gen_salt('bf', 10)),
         p_user_fname,
         p_user_lname,
         p_user_fname || '@test.com',
         'ROLE_BASIC_USER',
         now(),
         now(),
         NULL,
         NULL,
         'ACTIVE');

    FOR i IN 1..array_upper(p_permissions, 1) LOOP
        INSERT INTO forum_post.user_permissions
            (user_id, permission_id, permission_layer,
             date_created, last_date_modified, created_by, last_modified_by, entity_status)
        VALUES (
            (SELECT id FROM forum_post.forum_users WHERE username = p_username),
            (SELECT id FROM forum_post.permissions WHERE title = p_permissions[i]),
            p_perm_layers,
            now(),
            now(),
            NULL,
            NULL,
            'ACTIVE'
        );
    END LOOP;
END;
$$;