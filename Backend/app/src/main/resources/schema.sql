-- Drop existing tables if they exist
DROP TABLE IF EXISTS users CASCADE;

-- Create users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    user_name VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    street VARCHAR(255),
    suite VARCHAR(255),
    city VARCHAR(255),
    zipcode VARCHAR(255),
    lat VARCHAR(255),
    lng VARCHAR(255),
    phone VARCHAR(255),
    website VARCHAR(255),
    company_name VARCHAR(255),
    company_catch_phrase VARCHAR(255),
    company_bs VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS auth_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
); 