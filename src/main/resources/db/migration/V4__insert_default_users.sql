INSERT INTO users (name, email, password, enabled)
VALUES ('System Admin', 'admin@vehiclemanagement.com',
        '$2b$12$kFqVNKX3qgLQNKPklERpPuMoXs5fh0XTNbdUDLC3KWo424fZkLAIO', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('System User', 'user@vehiclemanagement.com',
        '$2b$12$fHBgejAuTrjrJvaX7JtJ.uCbUTA6W7bn3VyDmd8aLK5.A5TB3KPoW', true);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ADMIN'
WHERE u.email = 'admin@vehiclemanagement.com';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'USER'
WHERE u.email = 'user@vehiclemanagement.com';
