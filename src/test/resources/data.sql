
-- Insert data into the users table
INSERT INTO users (username, password, email, full_name)
VALUES ('doctorUser', 'password', 'doctor@example.com', 'Dr. John Doe');

-- Insert data into the roles table
INSERT INTO roles (role_type)
VALUES ('DOCTOR');

-- Insert data into the users_roles table
INSERT INTO users_roles (user_id, roles_id)
VALUES (
           (SELECT id FROM users WHERE username = 'doctorUser'),
           (SELECT id FROM roles WHERE role_type = 'DOCTOR')
       );

-- Insert data into the consultations table
INSERT INTO consultations (date_time, is_accepted, is_consulted, user_id)
VALUES
    ('2024-07-20 09:00:00', NULL, NULL, (SELECT id FROM users WHERE username = 'doctorUser')),
    ('2024-07-21 10:00:00', TRUE, NULL, (SELECT id FROM users WHERE username = 'doctorUser')),
    ('2024-07-22 11:00:00', FALSE, NULL, (SELECT id FROM users WHERE username = 'doctorUser'));
