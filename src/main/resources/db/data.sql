
TRUNCATE TABLE voters CASCADE;
TRUNCATE TABLE admin CASCADE;
TRUNCATE TABLE elections CASCADE;
TRUNCATE TABLE candidates CASCADE;
TRUNCATE TABLE votes CASCADE;


INSERT INTO voters(id, name, identification_number, password, address, date_of_birth,
                   state_of_origin, status, voting_id, role, date_registered) VALUES
(100, 'Jane Doe', '123456', 'password', '456 Elm St', '1985-02-15', 'Lagos', true, 654321, 'VOTER', '2023-01-01T00:00:00'),
(101, 'John Doe', '123457', 'password', 'address', '2000-07-20', 'Kano', true, 654322, 'VOTER', '2023-01-01T00:00:00'),
(102, 'Johnny Doe', '123458', 'password', 'address', '1992-04-07', 'Bauchi', true, 654323, 'VOTER', '2023-01-01T00:00:00'),
(103, 'Jean Grey', '123459', 'password', 'address', '2002-12-31', 'Enugu', true, 654324, 'VOTER', '2023-01-01T00:00:00'),
(104, 'Janet Doe', '123450', 'password', 'address', '1976-10-01', 'Ogun', true, 654325, 'VOTER', '2023-01-01T00:00:00'),
(105, 'Joe Doe', '123451', '$2a$10$liFcMrm6W5tZBDxbMlkfUeajKXhthr.U8R5znNKhXygrInpnmW6we', 'address',
 '1962-05-23', 'Bayelsa', true, 654326, 'VOTER', '2023-01-01T00:00:00'),
(106, 'Jon Doe', '123452', 'password', 'address', '1984-01-12', 'Kogi', true, 654327, 'VOTER', '2023-01-01T00:00:00');

INSERT INTO admin(id, address, username, password, role, date_registered) VALUES
(200, 'address', 'username', '$2a$10$liFcMrm6W5tZBDxbMlkfUeajKXhthr.U8R5znNKhXygrInpnmW6we', 'ADMIN', '2024-06-04T15:03:03.792009700');

INSERT INTO elections(id, title, category, start_date, end_date) VALUES
(300, 'title', 'LGA', '2024-06-04T15:03:03.792009700', '2024-06-19 12:00:00.000000'),
(301, 'State', 'STATE', '2027-08-22T15:03:03.792009700', '2027-09-21 12:00:00.000000'),
(302, 'National', 'NATIONAL', '2024-03-22T15:03:03.792009700', '2024-04-21 12:00:00.000000');

INSERT INTO candidates(id, name, identification_number, password, address, date_of_birth,
                       state_of_origin, voting_id, role, party_affiliation, position_contested,
                       election_id, date_registered) VALUES
(400, 'name', '401104', 'password', 'address', '1965-05-30', 'Kano', 401104, 'CANDIDATE', 'PDP', 'STATE', 301, '2023-01-01T00:00:00'),
(401, 'name1', '401105', 'password', 'address', '1955-09-30', 'Sokoto', 401105, 'CANDIDATE', 'Party 1', 'NATIONAL', 302, '2023-01-01T00:00:00'),
(402, 'name2', '401106', 'password', 'address', '1962-11-23', 'Benue', 401106, 'CANDIDATE', 'Party 2', 'NATIONAL', 302, '2023-01-01T00:00:00'),
(403, 'name3', '401107', 'password', 'address', '1979-01-08', 'Ondo', 401107, 'CANDIDATE', 'Party 3', 'NATIONAL', 302, '2023-01-01T00:00:00');

INSERT INTO votes(id, voter_id, candidate_id, election_id, date_casted) VALUES
(500, 100, 401, 302, '2024-03-22T16:03:03.792009700'),
(501, 101, 403, 302, '2024-03-22T16:03:03.792009700'),
(502, 102, 401, 302, '2024-03-22T16:03:03.792009700'),
(503, 103, 402, 302, '2024-03-22T16:03:03.792009700'),
(504, 104, 402, 302, '2024-03-22T16:03:03.792009700'),
(505, 105, 401, 302, '2024-03-22T16:03:03.792009700'),
(506, 106, 403, 302, '2024-03-22T16:03:03.792009700');
