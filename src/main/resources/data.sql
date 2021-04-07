INSERT IGNORE INTO users
    (user_id, is_account_non_expired,
     is_account_non_locked, is_credentials_non_expire,
     is_enabled, password, username)
values
    (0, true,
    true, true,
    true, '$2a$08$l/aYVaxb9P4l.N0kXFYtpOdTOvBx.haX7wf8ONu5U81KTCDFdNJsS', 'administrator');



INSERT INTO users_roles (user_id, roles) Select 0, 'ADMIN'
WHERE NOT EXISTS(select * from users_roles where user_id = 0 AND roles = 'ADMIN');

INSERT INTO users_roles (user_id, roles) Select 0, 'USER'
WHERE NOT EXISTS(select * from users_roles where user_id = 0 AND roles = 'USER')
