INSERT INTO users (name, email, password, enabled)
VALUES ('System Admin', 'admin@vehiclemanagement.com',
        '$2b$12$kFqVNKX3qgLQNKPklERpPuMoXs5fh0XTNbdUDLC3KWo424fZkLAIO', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('System User', 'user@vehiclemanagement.com',
        '$2b$12$fHBgejAuTrjrJvaX7JtJ.uCbUTA6W7bn3VyDmd8aLK5.A5TB3KPoW', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('Manager User', 'manager@vehiclemanagement.com',
        '$2b$12$OC18Okr8IjgWGx28AJQlGOlO9erD9XoyqOO95lfklGJ9QNG3WUQZe', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('Viewer User', 'viewer@vehiclemanagement.com',
        '$2b$12$85PFYtoihvfnDcdYFQUYWupyBcV52Xsdo2PghTIFPM8C8sP/XLwqi', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('Tester User', 'tester@vehiclemanagement.com',
        '$2b$12$kN69HzRLfpjp1GcxO4JD2eNry609Syvr7iKaKm5aZ68LTtUbXvbvS', true);

INSERT INTO users (name, email, password, enabled)
VALUES ('Guest User', 'guest@vehiclemanagement.com',
        '$2b$12$c0rAgQBCDFXMtukx6ffAMem5qkHiymzeA03H.YrHO0gSo8H0zm2NW', true);

-- ADMIN

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ADMIN'
WHERE u.email = 'admin@vehiclemanagement.com';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ADMIN'
WHERE u.email = 'manager@vehiclemanagement.com';

-- USER

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'USER'
WHERE u.email = 'user@vehiclemanagement.com';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'USER'
WHERE u.email = 'viewer@vehiclemanagement.com';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'USER'
WHERE u.email = 'tester@vehiclemanagement.com';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'USER'
WHERE u.email = 'guest@vehiclemanagement.com';