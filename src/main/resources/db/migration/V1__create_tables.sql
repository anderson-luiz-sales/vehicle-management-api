CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    NAME       VARCHAR(150) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    enabled    BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE vehicles
(
    id            BIGSERIAL PRIMARY KEY,
    brand         VARCHAR(100) NOT NULL,
    model         VARCHAR(100) NOT NULL,
    year          INTEGER NOT NULL,
    color         VARCHAR(50) NOT NULL,
    license_plate VARCHAR(10) NOT NULL UNIQUE,
    price_usd     NUMERIC(12, 2) NOT NULL,
    active        BOOLEAN NOT NULL DEFAULT true,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vehicle_brand ON vehicles(brand);

CREATE INDEX idx_vehicle_year ON vehicles(year);

CREATE INDEX idx_vehicle_color ON vehicles(color);

CREATE INDEX idx_vehicle_price ON vehicles(price_usd);