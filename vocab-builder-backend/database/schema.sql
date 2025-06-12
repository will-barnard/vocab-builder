BEGIN TRANSACTION;

DROP TABLE IF EXISTS definitions;
DROP TABLE IF EXISTS antonyms;
DROP TABLE IF EXISTS synonyms;
DROP TABLE IF EXISTS meanings;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	email varchar(100) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE words (
    word_id SERIAL PRIMARY KEY,
    word varchar(50) NOT NULL,
    user_id int NOT NULL REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE meanings (
    meaning_id SERIAL PRIMARY KEY,
    word_id int NOT NULL REFERENCES words(word_id) ON DELETE CASCADE,
    part_of_speech varchar(20) NOT NULL,
    phonetic varchar(50)
);

CREATE TABLE synonyms (
    synonym_id SERIAL PRIMARY KEY,
    meaning_id int NOT NULL REFERENCES meanings(meaning_id) ON DELETE CASCADE,
    synonym_word varchar(50) NOT NULL
);

CREATE TABLE antonyms (
    antonym_id SERIAL PRIMARY KEY,
    meaning_id int NOT NULL REFERENCES meanings(meaning_id) ON DELETE CASCADE,
    antonym_word varchar(50) NOT NULL
);

CREATE TABLE definitions (
    definition_id SERIAL PRIMARY KEY,
    meaning_id int NOT NULL REFERENCES meanings(meaning_id) ON DELETE CASCADE,
    definition_text varchar(2000) NOT NULL,
    example_sentence varchar(2000) NOT NULL
);


COMMIT TRANSACTION;
