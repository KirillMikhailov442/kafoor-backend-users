INSERT INTO roles (id, created_at, name, updated_at) VALUES
(1, '2026-01-26 18:15:12.000000', 'USER',    '2026-01-26 18:15:12.000000'),
(2, '2026-01-26 18:15:12.000000', 'ADMIN',   '2026-01-26 18:15:12.000000'),
(3, '2026-01-26 18:15:12.000000', 'STUDENT', '2026-01-26 18:15:12.000000'),
(4, '2026-01-26 18:17:52.000000', 'TEACHER', '2026-01-26 18:17:52.000000');

INSERT INTO users (id, created_at, deactivated_at, email, is_confirmed, name, nickname, password_hash, updated_at) VALUES
(1, '2026-01-26 18:15:13.000000', NULL, 'adminStudent@gmail.com', b'0', 'admin-student', '@adminStudent', '$2a$10$3OK3lJJtZsYgIgvgUDjyueTgOrzuJW3mJPaHymGD3OY97feO0vkmS', '2026-01-26 18:15:13.000000'),
(2, '2026-01-26 18:17:52.000000', NULL, 'adminTeacher@gmail.com', b'0', 'admin-teacher', '@adminTeacher', '$2a$10$eHoWGs3sIhV7BR1XdhH5nu1jxcbX0M5b.YaB1lFP/CyMGpddg4rxG', '2026-01-26 18:17:52.000000'),
(3, '2026-01-26 18:19:59.000000', NULL, 'bob@gmail.com',          b'0', 'bob',           '@bob',         '$2a$10$rKOxgcgSUwYMuc9SOkyFcuFTjNR89irdrtiD9eYnF6Jx6RmBdyjTi', '2026-01-26 18:19:59.000000'),
(4, '2026-01-26 18:30:34.000000', NULL, 'alex@gmail.com',        b'0', 'alex',          '@alex',        '$2a$10$L/.1WgQXRpgwFfnCzbSM9e7eJApVs6cB/jeWphlJZ4oz7B0Xa2DQa', '2026-01-26 18:30:34.000000'),
(5, '2026-01-26 18:37:10.000000', NULL, 'deril@gmail.com',       b'0', 'deril',         '@deril',       '$2a$10$.z0YpMQs82Qak8coXyQ.4OCrKjWTS5MlKH6M3Wr9jm/axFW5.vJOu', '2026-01-26 18:37:10.000000'),
(6, '2026-01-26 18:37:27.000000', NULL, 'max@gmail.com',         b'0', 'max',           '@max',         '$2a$10$idAgCOEYiOV7btor.vmReeWXkwno44OEC6lYYdJ2hcGXG..Zfn.tC', '2026-01-26 18:37:27.000000');

INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2), (2, 4),
(3, 1), (3, 2), (3, 3),
(4, 1), (4, 2), (4, 3),
(5, 1), (5, 3),
(6, 1), (6, 3);

INSERT INTO tokens (id, created_at, ip, refresh_token, updated_at, user_agent, user_id) VALUES
(1, '2026-01-26 18:15:13.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzY5NDMzMzEzLCJleHAiOjE3Njk0MzY5MTN9.S5X--IQGmoK9A1E86tKIhI5GlFz9YbceQ84Jrs8nNjQ', '2026-01-26 18:15:13.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 1),
(2, '2026-01-26 18:17:52.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNzY5NDMzNDcyLCJleHAiOjE3Njk0MzcwNzJ9.MF3Dv8TDE_ZgueR6JKcyibpN2zBa3DQhLi3mwiowdyk', '2026-01-26 18:17:52.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 2),
(3, '2026-01-26 18:19:59.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9TVFVERU5UIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifSx7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sInN1YiI6IjMiLCJpYXQiOjE3Njk0MzcwODEsImV4cCI6MTc2OTQ0MDY4MX0.fH9idUZi9bvF_lRvQe3kO9k1bLYzxxhR2GgWMrdq8No', '2026-01-26 19:18:01.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 3),
(4, '2026-01-26 18:30:34.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNzY5NDM0MjM0LCJleHAiOjE3Njk0Mzc4MzR9.wjpYs2gS9REExmlU4COfFsDvgh_WOSiHX2Fl_BRUeGo', '2026-01-26 18:30:34.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 4),
(5, '2026-01-26 18:37:10.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNzY5NDM0NjMwLCJleHAiOjE3Njk0MzgyMzB9.920gIgK6iOv1Z8QyXmcpNGLCo26AWu2uv4Y5XPO6TqM', '2026-01-26 18:37:10.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 5),
(6, '2026-01-26 18:37:27.000000', '0:0:0:0:0:0:0:1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNzY5NDM0NjQ3LCJleHAiOjE3Njk0MzgyNDd9.3r6O6VeonQT7ZDsJ1gS6BXMIrDUD2OC_SFK0pKun50M', '2026-01-26 18:37:27.000000', 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36', 6);