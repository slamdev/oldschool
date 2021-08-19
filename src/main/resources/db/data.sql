INSERT INTO product (id, name, description, version, created_date, last_modified_date)
VALUES ('e4fc35be-99a6-48d2-9a97-61c86681e7b8', 'Boat', 'A boat is a watercraft of a large range of types and sizes.',
        0, '2021-01-01 00:00:00+00', '2021-01-01 00:00:00+00'),
       ('187e7e51-78fc-4e3d-951e-7fe66889c357', 'Car', 'A car is a wheeled motor vehicle used for transportation.',
        0, '2021-01-01 00:00:00+00', '2021-01-01 00:00:00+00'),
       ('543ab991-67de-466e-b52a-b5bbce045a72', 'Bike',
        'A bike is a human-powered or motor-powered, pedal-driven, single-track vehicle.',
        0, '2021-01-01 00:00:00+00', '2021-01-01 00:00:00+00')
ON CONFLICT DO NOTHING;

INSERT INTO users (username, password, enabled)
VALUES ('user@oldschool.com', '$2a$10$mSR8mSrg92wz1xM.LK1GNudAwskEqPaYU6nOI/hEFkm5g22rwNgpi', true) -- password: user
ON CONFLICT DO NOTHING;

INSERT INTO authorities (username, authority)
VALUES ('user@oldschool.com', 'ROLE_USER')
ON CONFLICT DO NOTHING;
