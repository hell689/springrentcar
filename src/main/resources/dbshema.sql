DROP ALL OBJECTS;

CREATE TABLE cars
(
  id bigint NOT NULL AUTO_INCREMENT,
  mark bigint NOT NULL,
  gearbox bigint NOT NULL,
  volume float NOT NULL,
  color bigint NOT NULL,
  CONSTRAINT id PRIMARY KEY (id)
);

CREATE TABLE colors
(
  id bigint NOT NULL AUTO_INCREMENT,
  color char(50) NOT NULL,
  CONSTRAINT pk_colors PRIMARY KEY (id)
);

CREATE TABLE gearboxes
(
  id bigint NOT NULL AUTO_INCREMENT,
  gearbox char(50) NOT NULL,
  CONSTRAINT pk_gearboxes PRIMARY KEY (id)
);

CREATE TABLE marks
(
  id bigint NOT NULL AUTO_INCREMENT,
  mark char(255) NOT NULL,
  CONSTRAINT pk_marks PRIMARY KEY (id)
);

CREATE TABLE rents
(
  id bigint NOT NULL AUTO_INCREMENT,
  request bigint,
  car bigint,
  start_date datetime NOT NULL,
  end_date datetime NOT NULL,
  CONSTRAINT pk_rents PRIMARY KEY (id)
);

CREATE TABLE requests
(
  id bigint NOT NULL AUTO_INCREMENT,
  mark bigint,
  gearbox bigint,
  volume float,
  color bigint,
  start_date datetime NOT NULL,
  end_date datetime NOT NULL,
  comment char(255),
  username VARCHAR(45) NOT NULL,
  processed bool default false,
  CONSTRAINT pk_requests PRIMARY KEY (id)
);

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username));

  CREATE TABLE user_roles (
    user_role_id bigint NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    role varchar(45) NOT NULL,

    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_user_role (role,username),
    CONSTRAINT fk_user FOREIGN KEY (username) REFERENCES users (username));

ALTER TABLE cars ADD CONSTRAINT fk_cars_color
  FOREIGN KEY (color) REFERENCES colors (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE cars ADD CONSTRAINT fk_cars_gearbox
  FOREIGN KEY (gearbox) REFERENCES gearboxes (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE cars ADD CONSTRAINT fk_cars_mark
  FOREIGN KEY (mark) REFERENCES marks (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE rents ADD CONSTRAINT fk_rents_car
  FOREIGN KEY (car) REFERENCES cars (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE rents ADD CONSTRAINT fk_rents_request
  FOREIGN KEY (request) REFERENCES requests (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE requests ADD CONSTRAINT fk_requests_color
  FOREIGN KEY (color) REFERENCES colors (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE requests ADD CONSTRAINT fk_requests_gearbox
  FOREIGN KEY (gearbox) REFERENCES gearboxes (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE requests ADD CONSTRAINT fk_requests_mark
  FOREIGN KEY (mark) REFERENCES marks (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE requests ADD CONSTRAINT fk_requests_user
  FOREIGN KEY (username) REFERENCES users (username) ON DELETE RESTRICT ON UPDATE CASCADE;
