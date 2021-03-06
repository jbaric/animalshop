INSERT INTO ANIMAL_CATEGORY(NAME)
VALUES ('Dog');
INSERT INTO ANIMAL_CATEGORY(NAME)
VALUES ('Cat');
INSERT INTO ANIMAL_CATEGORY(NAME)
VALUES ('Others');

INSERT INTO USER(ID, USERNAME, EMAIL, PASSWORD_HASH)
VALUES(1, 'Juraj', 'jbaric@insolis.sk', '$2a$10$awwucMucvzuBnsglsFNw3uSHf5w7I2yO7gqcBG94/K1SBgH8wimMS');
INSERT INTO USER(ID, USERNAME, EMAIL, PASSWORD_HASH)
VALUES(2, 'Fero', 'fero@fero.sk', '$2a$10$L1WZ/4RLZeZFmDM05uJQhOW9OxX5A7TCw08eLHEDfUmsXv009u8ke');

INSERT INTO PRODUCT(ID, NAME, LOWER_NAME, PRICE, DESCRIPTION)
VALUES(1, 'Ball', 'ball', 5, 'For pets to play');
INSERT INTO PRODUCT(ID, NAME, LOWER_NAME, PRICE, DESCRIPTION)
VALUES(2, 'Mouse doll', 'mouse doll', 8, 'For cat to train hunting');
INSERT INTO PRODUCT(ID, NAME, LOWER_NAME, PRICE, DESCRIPTION)
VALUES(3, 'Hay', 'hay', 15, '50 kg haystack for horses, cows, etc.');

INSERT INTO PRODUCT_CATEGORY(PRODUCT_ID, CATEGORY_NAME)
VALUES(1, 'Dog');
INSERT INTO PRODUCT_CATEGORY(PRODUCT_ID, CATEGORY_NAME)
VALUES(1, 'Cat');
INSERT INTO PRODUCT_CATEGORY(PRODUCT_ID, CATEGORY_NAME)
VALUES(2, 'Cat');
INSERT INTO PRODUCT_CATEGORY(PRODUCT_ID, CATEGORY_NAME)
VALUES(3, 'Others');

INSERT INTO PRODUCT_GALLERY(PRODUCT_ID, URL)
VALUES(1, '/images/ball1.jpg');
INSERT INTO PRODUCT_GALLERY(PRODUCT_ID, URL)
VALUES(1, '/images/ball2.jpg');
INSERT INTO PRODUCT_GALLERY(PRODUCT_ID, URL)
VALUES(1, '/images/ball3.jpg');