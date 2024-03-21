-- Active: 1710817928357@@127.0.0.1@5432@kv20kh@public
-- *******************************************
-- Drop any previous tables

DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS title;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS tag_article_mapping;

-- ***********************************
-- Let's create some tables

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    age INT
);

CREATE TABLE article (
    user_id INT NOT NULL,
    text VARCHAR(256),
    date TIMESTAMP
);

CREATE TABLE tag (
    text VARCHAR(25)
);

CREATE TABLE title (
    id SERIAL PRIMARY KEY,
    text VARCHAR(100),
    article_id INT NOT NULL
);

-- will throw ERROR
CREATE TABLE tag_article_mapping (
    tag_id INT PRIMARY KEY,
    article_id INT PRIMARY KEY
); -- will throw ERROR

CREATE TABLE tag_article_mapping (
    tag_id INT NOT NULL,
    article_id INT NOT NULL,
    PRIMARY KEY (tag_id, article_id)
);

ALTER TABLE article ADD id SERIAL PRIMARY KEY;
ALTER TABLE tag ADD id SERIAL PRIMARY KEY;

ALTER TABLE article ADD COLUMN location VARCHAR(50);

----------------------------------------------------------

INSERT INTO users (first_name, last_name, age) 
VALUES 
    ('Pranjal', 'Chakraborty', 99),
    ('Karl', 'Grantham', 88),
    ('Amirmahdi Khosravi', 'Tabrizi', 77);

SELECT * FROM users;
SELECT first_name FROM users;
SELECT first_name FROM users WHERE age > 90;

-- ************************
-- Let's create some tags
INSERT INTO tag (text)
VALUES
    ('Canada'), 
    ('USA'), 
    ('Travel');

-- ************************
-- Okay, first article
INSERT INTO article (user_id, text, location, date)
VALUES (
    1,
    'Alberta is beautiful around this time of the year.',
    'Toronto, ON',
    now()
);

SELECT * FROM article;

INSERT INTO title (text, article_id)
VALUES ('Best places to visit in Alberta', 1);

INSERT INTO tag_article_mapping (article_id, tag_id)
VALUES
    (1, 1), (1, 3);


-- Second article
INSERT INTO article (user_id, text, location, date)
VALUES (
    3,
    'Violence is spreading across NEw York',
    'Buffalo, NY',
    now()
);

SELECT * FROM article;

INSERT INTO title (text, article_id)
VALUES ('NY is not what it used to be', 2);

SELECT * FROM tag;

INSERT INTO tag_article_mapping (article_id, tag_id)
VALUES (2, 2);

-- Third article
INSERT INTO article (user_id, text, location, date)
VALUES (
    2,
    'This might be the perfect time to visit this side of the world...',
    'Toronto, ON',
    now()
);

SELECT * FROM article;

INSERT INTO title (text, article_id)
VALUES ('North American travel destinations', 3);

INSERT INTO tag_article_mapping (article_id, tag_id)
VALUES
    (3, 1), (3, 2), (3, 3);

-- *******************************************
-- Now let's explore how many ways we can 
-- see data

SELECT * FROM article;

-- with Author name

SELECT * FROM article a
JOIN users u ON a.user_id = u.id;

-- what if there were more users in the table
INSERT INTO users (first_name, last_name, age) 
VALUES 
    ('Naser', 'Ezzati-Jivan', 66),
    ('Earl', 'Foxwell', 55);

-----------------------------------------------------------------
-- E is there to deal with escape character',
INSERT INTO article (user_id, text, location, date)
VALUES (
    2,
    E'Who does not love to travel?',
    'Niagara Falls, ON',
    now()
);

SELECT * FROM article;

INSERT INTO title (text, article_id)
VALUES ('Traveling might help you live longer', 4);

INSERT INTO tag_article_mapping (article_id, tag_id)
VALUES
    (4, 3);

-----------------------------------------------------------------
SELECT 
    ti.text AS title,
    a.text AS text, 
    a.location AS location, 
    u.first_name AS author, 
    string_agg(t.text, ' ,') AS tags 
FROM article a
JOIN users u ON a.user_id = u.id
JOIN tag_article_mapping tam ON a.id = tam.article_id
JOIN tag t ON t.id = tam.tag_id
JOIN title ti ON ti.article_id = a.id
GROUP BY ti.text, a.text, a.location, u.first_name;

-----------------------------------------------------------------
SELECT 
    u.first_name AS name, 
    COUNT(a.id) AS article_count
FROM users u
LEFT JOIN article a ON a.user_id = u.id
GROUP BY u.first_name
ORDER BY article_count DESC;

-- Upto line 187, we created some demo data so that
-- we can use those for Lab 4.

-- Views
-----------------------------------------------------------------

CREATE VIEW article_list AS
    SELECT 
        ti.text AS title,
        a.text AS text, 
        a.location AS location, 
        u.first_name AS author, 
        string_agg(t.text, ' ,') AS tags 
    FROM article a
    JOIN users u ON a.user_id = u.id
    JOIN tag_article_mapping tam ON a.id = tam.article_id
    JOIN tag t ON t.id = tam.tag_id
    JOIN title ti ON ti.article_id = a.id
    GROUP BY ti.text, a.text, a.location, u.first_name;

SELECT * FROM article_list;

-- Window functions
-----------------------------------------------------------

-- Just prints the average title length of each location, 
-- but we've lost individual article's data. Whatever you
-- now add in the select, it needs to be added in the group by.
SELECT location, avg(length(title))
FROM article_list
GROUP BY location;

-- To retain each row's data, while having some aggregator
-- function based on some other criteria (location, in this case).
SELECT location, avg(length(title)) 
OVER (PARTITION BY location)
FROM article_list;

-- Inheritance
---------------------------------------------------------------

SELECT * from users;

CREATE TABLE admin (
    key VARCHAR(256)
) INHERITS (users);

-- Procedure (it does not have return value), for return value,
-- use Function
---------------------------------------------------------------

CREATE OR REPLACE PROCEDURE add_user (
    fname VARCHAR(50),
    lname VARCHAR(50),
    uage INT
)
LANGUAGE SQL
AS 
$$
INSERT INTO users (first_name, last_name, age) 
VALUES (fname, lname, uage)
$$;

CALL add_user('Dave', 'Bockus', 999);

SELECT * FROM users;