-- Заполнение Авторов
INSERT INTO author (name, surname)
VALUES ('AuthorOne', 'OneOne');

INSERT INTO author (name, surname)
VALUES ('AuthorTwo', 'TwoTwo');

INSERT INTO author (name, surname)
VALUES ('AuthorThree', 'ThreeThree');

-- Заполнение Книг
INSERT INTO book (title, year, author_id)
VALUES ('BookOne', 1998, 2);

INSERT INTO book (title, year, author_id)
VALUES ('BookTwo', 1875, 3);

INSERT INTO book (title, year, author_id)
VALUES ('BookThree', 1765, 1);

INSERT INTO book (title, year)
VALUES ('BookFour', 2022);

-- Создание Пользователей
INSERT INTO usr (username, password, active)
VALUES ('user', 'user', true);

INSERT INTO usr (username, password, active)
VALUES ('admin', 'admin', true);