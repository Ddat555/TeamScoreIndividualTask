SET client_encoding = 'UTF8';
SET NAMES 'UTF8';

CREATE SCHEMA IF NOT EXISTS develop;

CREATE TABLE develop.category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    color VARCHAR(50),
    description TEXT
);

CREATE TABLE develop.type_payment (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE develop.cost (
    id BIGSERIAL PRIMARY KEY,
    date_time_pay TIMESTAMP NOT NULL,
    seller_name VARCHAR(255) NOT NULL,
    sum DECIMAL(19,2) NOT NULL,
    type_payment_id BIGINT REFERENCES develop.type_payment(id)
);

CREATE TABLE develop.category_cost (
    cost_id BIGINT NOT NULL REFERENCES develop.cost(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES develop.category(id) ON DELETE CASCADE,
    PRIMARY KEY (cost_id, category_id)
);