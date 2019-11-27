INSERT INTO colors (color) VALUES
('белый'), ('черный'), ('красный'), ('синий'), ('желтый'), ('зеленый'), ('серебристый');

INSERT INTO gearboxes (gearbox) VALUES
('ручная'), ('автомат'), ('полуавтомат');

INSERT INTO marks (mark) VALUES
('LADA'), ('Ferrari'), ('BMW'), ('Renault'), ('Seat'), ('Fiat'), ('Mazda'), ('Ford'), ('VolksWagen'), ('Mercedes');

INSERT INTO cars (mark, gearbox, volume, color) VALUES
(1, 1, 1.6, 2), (3, 1, 2.0, 4), (4, 2, 1.8, 1), (5, 1, 1.6, 3), (8, 2, 2.5, 5);


INSERT INTO users(username,password,enabled)
VALUES ('admin','$2y$12$sdk.RPfWd0yaVEnDCb0uTerI0iIdrty0P1qDy9RbvjAbIUYbbcu26', true);
INSERT INTO users(username,password,enabled)
VALUES ('user1','$2y$12$sdk.RPfWd0yaVEnDCb0uTerI0iIdrty0P1qDy9RbvjAbIUYbbcu26', true);

INSERT INTO user_roles (username, role)
VALUES ('user1', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO requests (mark, gearbox, volume, color, start_date, end_date, comment, username, processed) VALUES
(2, 1, 1.6, 2, '2019-08-09', '2019-08-11', 'Цвет можно любой', 'user1', true),
(1, 2, 1.8, 4, '2019-08-10', '2019-08-15', 'No comments', 'admin', false),
(4, 2, 1.6, 3, '2019-12-10', '2019-12-15', 'No comments', 'user1', false);

INSERT INTO rents (request, car, start_date, end_date) VALUES
(1, 1, '2019-08-09', '2019-08-11');