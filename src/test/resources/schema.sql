CREATE TABLE rates (
                       id         INT AUTO_INCREMENT PRIMARY KEY,
                       created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       name       VARCHAR(255)   NOT NULL,
                       value_Num  DECIMAL(10, 2) NOT NULL
);

-- Table: prices
CREATE TABLE prices (
                        id             INT AUTO_INCREMENT PRIMARY KEY,
                        price          DECIMAL(10, 2) NOT NULL,
                        procedure_type VARCHAR(255)   NOT NULL UNIQUE
);

-- Table: roles
CREATE TABLE roles (
                       id        INT AUTO_INCREMENT PRIMARY KEY,
                       role_type VARCHAR(255) CHECK (role_type IN ('ADMIN', 'USER', 'DOCTOR')) NOT NULL
);

-- Table: feedback
CREATE TABLE feedback (
                          id      INT AUTO_INCREMENT PRIMARY KEY,
                          email   VARCHAR(255),
                          message TEXT,
                          name    VARCHAR(255)
);

-- Table: users
CREATE TABLE users (
                       id        INT AUTO_INCREMENT PRIMARY KEY,
                       email     VARCHAR(255) NOT NULL UNIQUE,
                       full_name VARCHAR(255) NOT NULL,
                       password  VARCHAR(255) NOT NULL,
                       username  VARCHAR(255) NOT NULL UNIQUE
);

-- Table: consultations
CREATE TABLE consultations (
                               id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                               date_time    TIMESTAMP(6) NOT NULL,
                               is_accepted  INT     NULL,
                               is_consulted INT     NULL,
                               user_id      BIGINT      NULL,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Table: users_roles
CREATE TABLE users_roles (
                             user_id  INT NOT NULL,
                             roles_id INT NOT NULL,
                             PRIMARY KEY (user_id, roles_id),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE
);